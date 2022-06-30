package chess.pieces;

import boardgame.Bord;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Bord bord, Color color) {
		super(bord, color);
		
	}

	@Override
	public String toString() {
		return "B";
	}


	@Override
	public boolean[][] possibleMoves() {
		boolean[][]mat = new boolean[getBord().getRows()][getBord().getColumns()];
	    Position p = new Position(0, 0);
		
	    //nw
	    p.setValues(position.getRow()-1, position.getColumn() - 1);
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setValues(p.getRow() -1, p.getColumn() -1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		 //ne
	    p.setValues(position.getRow() -1 , position.getColumn() + 1);
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setValues(p.getRow() -1, p.getColumn() +1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		 //se
	    p.setValues(position.getRow() + 1, position.getColumn() + 1);
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setValues(p.getRow() +1, p.getColumn() +1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		 //sw
	    p.setValues(position.getRow() +1, position.getColumn() -1);
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setValues(p.getRow() +1, p.getColumn() -1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	
	
	}
	
}
