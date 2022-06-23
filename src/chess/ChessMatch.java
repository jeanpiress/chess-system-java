 package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Bord;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Bord bord;
	
	private List<Piece> piecesOnTheBord= new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
      
     
     public ChessMatch() {
    	 bord = new Bord(8, 8);
    	 turn = 1;
    	 currentPlayer = Color.WHITE;
    	 initialSetup();
     }
     
     public int getTurn() {
    	 return turn;
     }
     
     public Color getCurrentPlayer() {
    	 return currentPlayer;
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
    	return (ChessPiece)capturedPiece;
        }
    
    private Piece makeMove(Position source, Position target) {
    	Piece p = bord.removePiece(source);
    	Piece capturedPiece = bord.removePiece(target);
    	bord.placePiece(p, target);
    	nextTurn();
    	if(capturedPiece != null) {
    		piecesOnTheBord.remove(capturedPiece);
    		capturedPieces.add(capturedPiece);
    	}
    	
    	return capturedPiece;
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
    
    private void placeNewPiece(char column, int row, ChessPiece piece) {
    	 bord.placePiece(piece, new ChessPosition(column, row).toPosition());
    	 piecesOnTheBord.add(piece);
     }

      private void initialSetup() {
    	  placeNewPiece('c', 1, new Rook(bord, Color.WHITE));
          placeNewPiece('c', 2, new Rook(bord, Color.WHITE));
          placeNewPiece('d', 2, new Rook(bord, Color.WHITE));
          placeNewPiece('e', 2, new Rook(bord, Color.WHITE));
          placeNewPiece('e', 1, new Rook(bord, Color.WHITE));
          placeNewPiece('d', 1, new King(bord, Color.WHITE));

          placeNewPiece('c', 7, new Rook(bord, Color.BLACK));
          placeNewPiece('c', 8, new Rook(bord, Color.BLACK));
          placeNewPiece('d', 7, new Rook(bord, Color.BLACK));
          placeNewPiece('e', 7, new Rook(bord, Color.BLACK));
          placeNewPiece('e', 8, new Rook(bord, Color.BLACK));
          placeNewPiece('d', 8, new King(bord, Color.BLACK));
      }



}
