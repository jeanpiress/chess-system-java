 package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Bord;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Bord bord;
	private boolean check;
	private boolean checkMate;
	
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
    	check = (testCheck(opponent(currentPlayer))) ? true : false;
    	
    	if(testCheckMate(opponent(currentPlayer))) {
    		checkMate = true;
    	}
    	else {
    	nextTurn();
    	}
    	
    	return (ChessPiece)capturedPiece;
        }
    
   
       
     private Piece makeMove(Position source, Position target) {
    	Piece p = bord.removePiece(source);
    	Piece capturedPiece = bord.removePiece(target);
    	bord.placePiece(p, target);
    	if(capturedPiece != null) {
    		piecesOnTheBord.remove(capturedPiece);
    		capturedPieces.add(capturedPiece);
    	}
    	
    	return capturedPiece;
    }
    
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
    	Piece p = bord.removePiece(target);
    	bord.placePiece(p, source);
    	
    	if(capturedPiece != null) {
    		bord.placePiece(capturedPiece, target);
    		capturedPieces.remove(capturedPiece);
    		piecesOnTheBord.add(capturedPiece);
    		
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
    	  placeNewPiece('h', 7, new Rook(bord, Color.WHITE));
          placeNewPiece('d', 1, new Rook(bord, Color.WHITE));
          placeNewPiece('e', 1, new King(bord, Color.WHITE));

          placeNewPiece('b', 8, new Rook(bord, Color.BLACK));
          placeNewPiece('a', 8, new King(bord, Color.BLACK));
      }



}
