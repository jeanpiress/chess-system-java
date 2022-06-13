package boardgame;

public class Bord {

     private Integer rows;
     private Integer columns;
     private Piece[][] pieces;
	
     
     public Bord(Integer rows, Integer columns) {
    	 if(rows < 0 || columns < 0 ) {
    		 throw new BordException("Error creating bord: there must be at least 1 row and 1 column");
    	 }
		this.rows = rows;
		this.columns = columns;
        pieces = new Piece[rows][columns];	
     }


	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

     public Piece piece(int row, int column) {
    	if(!positionExists(row, column)) {
    		throw new BordException("position not on the bord");
    		
    	}
    	 return pieces[row][column];
     }
     
     public Piece piece(Position position) {
    	 if(!positionExists(position)) {
     		throw new BordException("position not on the bord");
    	 }
    	 return pieces[position.getRow()][position.getColumn()];
     }
     
    public void placePiece(Piece piece, Position position) {
    	if(thereIsAPiece(position)) {
    		throw new BordException("there is already a piece on position " + position);
    	}
    	pieces[position.getRow()][position.getColumn()] = piece;
    	piece.position = position;
    }
     
    private boolean positionExists(int row, int column) {
    	return row >= 0 && row < rows && column >= 0 && column < columns;
    }
     
    public boolean positionExists(Position position) {
    	return positionExists(position.getRow(), position.getColumn());
    }
    
    public boolean thereIsAPiece(Position position) {
    	 if(!positionExists(position)) {
      		throw new BordException("position not on the bord");
     	 }
    	return piece(position) != null;
    }
}
