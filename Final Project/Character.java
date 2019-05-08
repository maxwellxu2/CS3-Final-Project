import mayflower.*;
public class Character extends Actor
{
    //INSTANCE VARIABLES
    private int row, col;
    private boolean selected;
    private boolean attacking;
    private boolean highlighted;
    private boolean wasClicked;
    private int health;
    public Piece myPiece;

    //CONSTRUCTORS
    public Character(int row, int col) 
    {
        //initialize instance variables
        this.row = row;
        this.col = col;

        setPiece(null);
        selected = false;
        wasClicked = false;

        health = 100;
    }

    //METHODS
    public void update()
    {
        if(isClicked() && !wasClicked)
        {
            //Tell the stage that this piece was clicked
            MyStage stage = (MyStage)getStage();
            stage.click(row, col);

            wasClicked = true;
        }

        //set wasClicked to false if the mouse is not clicked
        wasClicked = isClicked();
    }    

    /**
     *  Change which piece this Character is displaying
     *      
     */
    public void setPiece(Piece piece)
    {
        myPiece = piece;

        String img = "img/empty";
        if(piece == Piece.ENEMY)
            img = "img/enemy";
        else if(piece == Piece.FRIENDLY)
            img = "img/hero";

        if(selected)
            img += "Selected";
        //img = "img/empty";
        if(attacking)
            img += "Attack";
        if(highlighted)
            img += "Highlighted";
            
        setPicture(img + ".png");
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
        setPiece(myPiece);
    }

    public void setAttacking(boolean attacking)
    {
        this.attacking = attacking;
        setPiece(myPiece);
    }

    public void setHighlighted(boolean highlighted)
    {
        this.highlighted = highlighted;
        setPiece(myPiece);
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public int setHealth()
    {
        return health;
    }

    public int getHealth()
    {
        return health;
    }

    public Piece getPiece()
    {
        return myPiece;
    }
}