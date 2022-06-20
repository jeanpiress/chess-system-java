package boardgame;

public abstract class  Piece {

	protected Position position;
	private Bord bord;
	
	
	public Piece(Bord bord) {
	  this.bord = bord;
	}


	protected Bord getBord() {
		return bord;
	}
	 
   public abstract boolean[][] possibleMoves();{
	   
   }

   public boolean possibleMoves(Position position) {
	   return possibleMoves()[position.getRow()][position.getColumn()]; 
   }
   
   public boolean isThereAnyPossibleMove() {
	   boolean[][] mat = possibleMoves();
	   for(int i = 0; i<mat.length; i++) {
		   for(int j=0; j<mat.length; j++) {
			   if(mat[i][j]) {
				   return true;
			   }
		   }
	   }
	   return false;
   }
	
}
