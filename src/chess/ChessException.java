package chess;

import boardgame.BordException;

public class ChessException extends BordException {
	private static final long serialVersionUID = 1L;

	public ChessException(String msg) {
		super(msg);
	}
}
