package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
     
		ChessMatch chessMatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			UI.printBord(chessMatch.getPieces());
			System.out.println();
			System.out.println("Source: ");
			ChessPosition souce = UI.readChessPosition(sc);
			
			System.out.println();
			System.out.println("Target: ");
			ChessPosition target = UI.readChessPosition(sc);
			
			
			ChessPiece capturedPiece = chessMatch.performChessMove(souce, target);
						
			

		}	
	}

}
