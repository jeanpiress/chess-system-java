 package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Bord;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Bord bord;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBord= new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
      
     
     public ChessMatch() {
    	 bord = new Bord(8, 8);
    	 turn = 1;
    	 currentPlayer = Color.WHITE;
    	 check = false;
    	 initialSetup();
     }
     
     public int getTurn() {
    	 return turn;
     }
     
     public Color getCurrentPlayer() {
    	 return currentPlayer;
     }
     
     public boolean getCheck() {
    	 return check;
     }
     
     public boolean getCheckMate() {
    	 return checkMate;
     }
     
     public ChessPiece getEnPassantVulnerable() {
    	 return enPassantVulnerable;
     }

     public ChessPiece getPromoted() {
    	 return promoted;
     }
     public ChessPiece[][] getPieces(){
    	 ChessPiece[][] mat = new ChessPiece[bord.getRows()][bord.getColumns()];
    	 for(int i = 0; i< bord.getRows(); i++) {
    		 for(int j = 0; j< bord.getColumns(); j++) {
    			 mat[i][j] = (ChessPiece) bord.piece(i,j);
    		 }
    	 }
    return mat;
     }
     
    
     public boolean[][] possibleMoves(ChessPosition sourcePosition){
    	 Position position = sourcePosition.toPosition();
    	 validateSourcePosition(position);
    	 return bord.piece(position).possibleMoves();    	 
     }
     
     
     
     
     public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    	Position source = sourcePosition.toPosition();
    	Position target = targetPosition.toPosition();
    	validateSourcePosition(source);
    	validateTargetPosition(source, target);
    	Piece capturedPiece = makeMove(source, target);
    	
    	if(testCheck(currentPlayer)) {
    		undoMove(source, target, capturedPiece);
    		throw new ChessException ("You can't put yourself in check");
    	}
    	
    	ChessPiece movedPiece = (ChessPiece)bord.piece(target);
    	
    	// #SpecialMove promotion
    	promoted = null;
    	if(movedPiece instanceof Pawn) {
    	   if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
    		   promoted = (ChessPiece)bord.piece(target);
    		   promoted = replacePromotedPiece("Q");
    	   }
    		
    	}
    	
    	
    	check = (testCheck(opponent(currentPlayer))) ? true : false;
    	
    	if(testCheckMate(opponent(currentPlayer))) {
    		checkMate = true;
    	}
    	else {
    	nextTurn();
    	}
    	
    	//#specialmove en passant
    	if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() -2  || target.getRow() == source.getRow() +2)){
    		enPassantVulnerable = movedPiece;
    	}
    	else {
    		enPassantVulnerable = null;
    	}
    	
    	
    	return (ChessPiece)capturedPiece;
        }
    
     public ChessPiece replacePromotedPiece(String type) {
    	 if(promoted == null) {
    		 throw new IllegalStateException("There is no piece to be promoted");
    	 }
    	 if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
    		 return promoted;
    		 }
    	 Position pos = promoted.getChessPosition().toPosition();
    	 Piece p = bord.removePiece(pos);
    	 piecesOnTheBord.remove(p);
    	 
    	 ChessPiece newPiece = newPiece(type, promoted.getColor());
    	 bord.placePiece(newPiece, pos);
    	 piecesOnTheBord.add(newPiece);
    	 
    	 return newPiece;
     
     
     }
     
     private ChessPiece newPiece(String type, Color color) {
    	 if(type.equals("B")) return new Bishop(bord, color);
    	 if(type.equals("N")) return new Knight(bord, color);
    	 if(type.equals("Q")) return new Queen(bord, color);
    	 return new Rook(bord, color);
     }
       
     private Piece makeMove(Position source, Position target) {
    	ChessPiece p = (ChessPiece)bord.removePiece(source);
    	p.increaseMoveCount();
    	Piece capturedPiece = bord.removePiece(target);
    	bord.placePiece(p, target);
    	if(capturedPiece != null) {
    		piecesOnTheBord.remove(capturedPiece);
    		capturedPieces.add(capturedPiece);
    	}
    	
    	//#special castling kingside rook
    	if(p instanceof King && target.getColumn() == source.getColumn() +2) {
    		Position sourceT = new Position(source.getRow(), source.getColumn() +3);
    		Position targetT = new Position(source.getRow(), source.getColumn() +1);
    		ChessPiece rook = (ChessPiece)bord.removePiece(sourceT);
    		bord.placePiece(rook, targetT);
    		rook.increaseMoveCount();
    	}
    	
    	//#special castling queenside rook
    	if(p instanceof King && target.getColumn() == source.getColumn() -2) {
    		Position sourceT = new Position(source.getRow(), source.getColumn() -4);
    		Position targetT = new Position(source.getRow(), source.getColumn() -1);
    		ChessPiece rook = (ChessPiece)bord.removePiece(sourceT);
    		bord.placePiece(rook, targetT);
    		rook.increaseMoveCount();
    	}
    	
    	// #special move en passant
    	if(p instanceof Pawn) {
    		if(source.getColumn() != target.getColumn() && capturedPiece == null) {
    			Position pawnPosition;
    			if(p.getColor() == Color.WHITE) {
    				pawnPosition = new Position(target.getRow() + 1, target.getColumn());
    			}
    			else {
    				pawnPosition = new Position(target.getRow() - 1, target.getColumn());
    			}
    			capturedPiece = bord.removePiece(pawnPosition);
    			capturedPieces.add(capturedPiece);
    			piecesOnTheBord.remove(capturedPiece);
    			
    		}
    	}
    	
    	
    	
    	
    	return capturedPiece;
    }
    
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
    	ChessPiece p =(ChessPiece) bord.removePiece(target);
    	p.decreaseMoveCount();
    	bord.placePiece(p, source);
    	
    	if(capturedPiece != null) {
    		bord.placePiece(capturedPiece, target);
    		capturedPieces.remove(capturedPiece);
    		piecesOnTheBord.add(capturedPiece);
    		
    	}
    	
    	//#special castling kingside rook
    	if(p instanceof King && target.getColumn() == source.getColumn() +2) {
    		Position sourceT = new Position(source.getRow(), source.getColumn() +3);
    		Position targetT = new Position(source.getRow(), source.getColumn() +1);
    		ChessPiece rook = (ChessPiece)bord.removePiece(targetT);
    		bord.placePiece(rook, sourceT);
    		rook.decreaseMoveCount();
    	}
    	
    	//#special castling queenside rook
    	if(p instanceof King && target.getColumn() == source.getColumn() -2) {
    		Position sourceT = new Position(source.getRow(), source.getColumn() -4);
    		Position targetT = new Position(source.getRow(), source.getColumn() -1);
    		ChessPiece rook = (ChessPiece)bord.removePiece(targetT);
    		bord.placePiece(rook, sourceT);
    		rook.decreaseMoveCount();
    	}
    	
    	// #special move en passant
    	if(p instanceof Pawn) {
    		if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
    			ChessPiece pawn = (ChessPiece)bord.removePiece(target);
    			Position pawnPosition;
    			if(p.getColor() == Color.WHITE) {
    				pawnPosition = new Position(3, target.getColumn());
    			}
    			else {
    				pawnPosition = new Position(4, target.getColumn());
    			}
    			bord.placePiece(pawn, pawnPosition);
    			
    			
    		}
    	}
    	
    }
    
    private void validateSourcePosition(Position position) {
    	if(!bord.thereIsAPiece(position)) {
    		throw new ChessException("There is no piece on source position");
    	}
    	if(currentPlayer != ((ChessPiece)bord.piece(position)).getColor()) {
    		throw new ChessException("The chosen piece is not yours");
    		
    	}
    	
    	if(!bord.piece(position).isThereAnyPossibleMove()) {
    		throw new ChessException("there is no possible moves for the chosen piece");
    	}
    }
     
    private void validateTargetPosition(Position source, Position target) {
    	if(!bord.piece(source).possibleMoves(target)) {
    		throw new ChessException("The chosen piece can't move to target position");
    	}
    	
    	
    }
     
   private void nextTurn() {
	   turn ++;
	   currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
   }
    
    
   private Color opponent(Color color) {
	   return (color == color.WHITE) ? color.BLACK : color.WHITE;
   }
   
   private ChessPiece king(Color color) {
	   List<Piece> list = piecesOnTheBord.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
	   for (Piece p: list) {
		   if(p instanceof King) {
			   return (ChessPiece)p;
		   }
	   }
   throw new IllegalStateException("There is no " + color + " King on the board");
   }
   
   private boolean testCheck(Color color) {
	   Position kingPosition = king(color).getChessPosition().toPosition();
	   List<Piece> opponentPieces =  piecesOnTheBord.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
	   for(Piece p : opponentPieces) {
		   boolean[][] mat = p.possibleMoves();
		   if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
			   return true;
		   }
	   }
	   
   return false;
   }
   
   private boolean testCheckMate(Color color) {
	   if(!testCheck(color)) {
		   return false;
	   }
	   List<Piece> list = piecesOnTheBord.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
	   for(Piece p : list) {
		   boolean[][] mat = p.possibleMoves();
		   for(int i = 0; i<bord.getRows(); i++) {
			   for(int j = 0; j<bord.getColumns(); j++) {
				   if(mat[i][j]) {
					   Position source = ((ChessPiece)p).getChessPosition().toPosition();
					   Position target = new Position(i, j);
					   Piece capturedPiece = makeMove(source, target);
					   boolean testCheck = testCheck(color);
					   undoMove(source, target, capturedPiece);
					   if(!testCheck) {
						   return false;
					   }
				   }
			   }
		   }
	   }
	   return true;
   }
   
   private void placeNewPiece(char column, int row, ChessPiece piece) {
    	 bord.placePiece(piece, new ChessPosition(column, row).toPosition());
    	 piecesOnTheBord.add(piece);
     }

      private void initialSetup() {
    	  placeNewPiece('a', 1, new Rook(bord, Color.WHITE));
    	  placeNewPiece('b', 1, new Knight(bord, Color.WHITE));
    	  placeNewPiece('c', 1, new Bishop(bord, Color.WHITE));
    	  placeNewPiece('d', 1, new Queen(bord, Color.WHITE));
          placeNewPiece('e', 1, new King(bord, Color.WHITE, this));
          placeNewPiece('f', 1, new Bishop(bord, Color.WHITE));
          placeNewPiece('g', 1, new Knight(bord, Color.WHITE));
          placeNewPiece('h', 1, new Rook(bord, Color.WHITE));
          placeNewPiece('a', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('b', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('c', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('d', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('e', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('f', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('g', 2, new Pawn(bord, Color.WHITE, this));
          placeNewPiece('h', 2, new Pawn(bord, Color.WHITE, this));

          placeNewPiece('a', 8, new Rook(bord, Color.BLACK));
          placeNewPiece('b', 8, new Knight(bord, Color.BLACK));
          placeNewPiece('c', 8, new Bishop(bord, Color.BLACK));
          placeNewPiece('d', 8, new Queen(bord, Color.BLACK));
          placeNewPiece('e', 8, new King(bord, Color.BLACK, this));
          placeNewPiece('f', 8, new Bishop(bord, Color.BLACK));
          placeNewPiece('g', 8, new Knight(bord, Color.BLACK));
          placeNewPiece('h', 8, new Rook(bord, Color.BLACK));
          placeNewPiece('a', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('b', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('c', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('d', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('e', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('f', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('g', 7, new Pawn(bord, Color.BLACK, this));
          placeNewPiece('h', 7, new Pawn(bord, Color.BLACK, this));
      }



}
