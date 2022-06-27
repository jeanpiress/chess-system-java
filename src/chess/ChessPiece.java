package chess;

import boardgame.Bord;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Color color;
	//private int moveCount;
	
	
	public ChessPiece(Bord bord, Color color) {
		super(bord);
		this.color = color;
	}


	public Color getColor() {
		return color;
	}

    public ChessPosition getChessPosition() {
    	return ChessPosition.fromPosition(position);
    }
	
	protected boolean isThereOpponentPiece(Position position) {
	 
		ChessPiece p = (ChessPiece)getBord().piece(position);
		return p != null && p.getColor() != color;
	}
	}
