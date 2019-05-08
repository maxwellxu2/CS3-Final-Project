import mayflower.*;

public class Enemy extends Character
{
    /**
     * Constructor for objects of class Enemy
     */
    public Enemy(int row, int col)
    {
        super(row, col);
        myPiece = Piece.ENEMY;
    }
}
