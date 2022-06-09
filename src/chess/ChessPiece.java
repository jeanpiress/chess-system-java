package chess;

import boardgame.Bord;
import boardgame.Piece;

public class ChessPiece extends Piece{
	
	private Color color;
	//private int moveCount;
	
	
	public ChessPiece(Bord bord, Color color) {
		super(bord);
		this.color = color;
	}


	public Color getColor() {
		return color;
	}


	}
