public class Attack 
{
	//INSTANCE VARIABLES
	private Point piece;		//The original location of the Piece
	private Point destination;	//The destination of the Piece

	//CONSTRUCTORS
    public Attack(Point piece, Point destination) 
    {
    	//initialize instance variables
    	this.piece = piece;
    	this.destination = destination;
    }
    
    //METHODS
    public Point getPiece()
    {
    	return piece;
    }
    
    public Point getDestination()
    {
    	return destination;
    }
    
    /**
     *	Two Move objects are equal if all of their Point instance variables are equal
     *	(see the .equals method of the Point class)
     */
    public boolean equals(Object other)
    {
    	if(null == other || !(other instanceof Move))
    		return false;
    	Move m = (Move)other;
    	
    	return piece.equals(m.getPiece()) 
    		&& destination.equals(m.getDestination());
    }
}