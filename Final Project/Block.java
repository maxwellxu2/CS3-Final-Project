import mayflower.*;
public class Block extends Actor
{
    private int row, col;
    
    /**
     * Constructor for objects of class Block
     */
    public Block(int row, int col)
    {
        Picture p = new Picture("img/SQUARE.png");
        setPicture(p);
    }

    public Block(int row, int col, String str)
    {
        if(str.equals("block"))
        {
            setPicture(new Picture("img/SQUARE.png"));
        }  
    }
    
    public void update()
    {
    }
}