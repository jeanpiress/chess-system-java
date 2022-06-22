package chess.pieces;

import boardgame.Bord;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Bord bord, Color color) {
		super(bord, color);

	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBord().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBord().getRows()][getBord().getColumns()];
		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBord().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBord().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBord().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBord().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBord().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// ne
				p.setValues(position.getRow() - 1, position.getColumn() + 1);
				if (getBord().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
		// sw
				p.setValues(position.getRow() + 1, position.getColumn() - 1);
				if (getBord().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}		
     
		// se
				p.setValues(position.getRow() + 1, position.getColumn() + 1);
				if (getBord().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}		
				
		return mat;
	}

}
