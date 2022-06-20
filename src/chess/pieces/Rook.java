package chess.pieces;

import boardgame.Bord;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Bord bord, Color color) {
		super(bord, color);
		
	}

	@Override
	public String toString() {
		return "R";
	}


	@Override
	public boolean[][] possibleMoves() {
		boolean[][]mat = new boolean[getBord().getRows()][getBord().getColumns()];
	    Position p = new Position(0, 0);
		
	    //above
	    p.setValues(position.getRow()-1, position.getColumn());
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setRow(p.getRow() - 1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		 //left
	    p.setValues(position.getRow(), position.getColumn() - 1);
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setColumn(p.getColumn() - 1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		 //right
	    p.setValues(position.getRow(), position.getColumn() + 1);
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setColumn(p.getColumn() + 1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		 //below
	    p.setValues(position.getRow()+1, position.getColumn());
	    while (getBord().positionExists(p) && !getBord().thereIsAPiece(p)){
	    	mat[p.getRow()][p.getColumn()] = true;
	    	p.setRow(p.getRow() + 1);
	    }
		
		if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	
	
	}
	
}
