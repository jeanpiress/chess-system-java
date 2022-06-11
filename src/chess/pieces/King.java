package chess.pieces;

import boardgame.Bord;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Bord bord, Color color) {
		super(bord, color);

	}

	@Override
	public String toString() {
		return "K";
	}

	
}
