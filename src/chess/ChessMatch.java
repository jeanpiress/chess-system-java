 package chess;

import boardgame.Bord;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

     private Bord bord;
     
     public ChessMatch() {
    	 bord = new Bord(8, 8);
    	 initialSetup();
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

      private void initialSetup() {
    	  bord.placePiece(new Rook(bord, Color.WHITE ), new Position(2,1));
    	  bord.placePiece(new King(bord, Color.BLACK), new Position(0,4));
    	  bord.placePiece(new King(bord, Color.WHITE), new Position(7,4));
      }



}
