import mayflower.*;

public class Friendly extends Character
{
    /**
     * Constructor for objects of class Friendly
     */
    public Friendly(int row, int col)
    {
        super(row, col);
        myPiece = Piece.FRIENDLY;
    }
}
