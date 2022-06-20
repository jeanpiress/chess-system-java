package chess.pieces;

import boardgame.Bord;
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
		return mat;
	}
}
