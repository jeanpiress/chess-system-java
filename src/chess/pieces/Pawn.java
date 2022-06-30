package chess.pieces;

import boardgame.Bord;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Bord bord, Color color) {
		super(bord, color);
		}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][]mat = new boolean[getBord().getRows()][getBord().getColumns()];
	    Position p = new Position(0, 0);
		
	    if(getColor() == Color.WHITE) {
		p.setValues(position.getRow() -1, position.getColumn());
	    	if(getBord().positionExists(p) && !getBord().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
	    	p.setValues(position.getRow() -2, position.getColumn());
	    	Position p2 = new Position(position.getRow() -1, position.getColumn());
			if(getBord().positionExists(p) && !getBord().thereIsAPiece(p) && getMoveCount() == 0 && getBord().positionExists(p2) && !getBord().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() -1, position.getColumn() -1);
	    	if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
	    	p.setValues(position.getRow() -1, position.getColumn() +1);
	    	if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
		}
	    else {
	    	p.setValues(position.getRow() +1, position.getColumn());
	    	if(getBord().positionExists(p) && !getBord().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
	    	p.setValues(position.getRow() +2, position.getColumn());
	    	Position p2 = new Position(position.getRow() +1, position.getColumn());
			if(getBord().positionExists(p) && !getBord().thereIsAPiece(p) && getMoveCount() == 0 && getBord().positionExists(p2) && !getBord().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() +1, position.getColumn() -1);
	    	if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
	    	p.setValues(position.getRow() +1, position.getColumn() +1);
	    	if(getBord().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
	    }
	    
	    
	    return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	

}
