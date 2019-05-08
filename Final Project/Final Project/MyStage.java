import mayflower.*;
import java.util.*;

public class MyStage extends Stage
{
    private GameLogic game;
    private Character[][] board;
    private Piece myPiece;

    private Point from = null;
    private Point to = null;

    public MyStage(GameLogic game)
    {
        setBackground("img/background-1.jpg");

        this.game = game;
        myPiece = Piece.FRIENDLY;

        Map ma = new Map();
        String[][] map = ma.getMap();
        //add blocks to the stage according to the map 2d array
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[0].length; c++)
            {
                if(map[r][c] != null)
                {
                    addActor(new Block(r, c, map[r][c]), 64+64*c, 64+64*r);
                }
            }
        }

        board = new Character[10][15];
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[0].length; c++)
            {
                Character actor = new Character(r, c);
                board[r][c] = actor;
                actor.setPiece(game.getPiece(new Point(r, c)));
                addActor(actor, c * 64 + 64, r * 64 + 64);
            }
        }

        /*Friendly hero = new Friendly(0, 5);
        hero.setPiece("hero");
        addActor(hero, 64+hero.getRow()*64, 64-16+hero.getCol()*64);*/
    }

    public void update()
    {
    }

    public void updatePiece(Point p, Piece piece)
    {
        Character actor = board[p.getRow()][p.getCol()];
        actor.setPiece(piece);
    }

    public List<Point> surroundingSquares(Point p)
    {
        List<Point> surround = new LinkedList<Point>();
        int row = p.getRow();
        int col = p.getCol();
        for(int r = row-1; r < row+2; r++)
        {
            for(int c = col-1; c < col+2; c++)
                if(r > -1 && r < 10 && c > -1 && c < 15)
                {
                    if(board[r][c].getPiece() != Piece.FRIENDLY && board[r][c].getPiece() != Piece.ENEMY)
                    {
                        surround.add(new Point(r,c));
                    }
                }
        }
        return surround;
    }

    public void highlightSurroundingSquares(List<Point> list, boolean var)
    {
        for(Point p : list)
        {
            board[p.getRow()][p.getCol()].setHighlighted(var);
        }
    }

    public void reset()
    {
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[0].length; c++)
            {
                board[r][c].setPiece(game.getPiece(new Point(r, c)));
                board[r][c].setSelected(false);
            }
        }

        from = null;
        to = null;
    }

    public void click(int row, int col)
    {
        Point p = new Point(row, col);
        if(game.getPhase() == 0)
        {
            if(null == from)
                clickFrom(p);
            else
                clickTo(p);
        }
        else if(game.getPhase() == 1)
        {
            clickAttack(p);
        }

    }

    private void clickFrom(Point p)
    {   
        if(game.getPhase() == 0)
        {
            from = p;

            System.out.println("From: " + p);

            board[p.getRow()][p.getCol()].setSelected(true);
            List<Point> list = surroundingSquares(p);
            highlightSurroundingSquares(list, true);
        }
    }

    private void clickTo(Point p)
    {
        if(game.getPhase() == 0)
        {
            System.out.println("To: " + p);
            if(game.getPiece(from) != null)
            {
                to = p;
                if(!from.equals(to))
                {
                    if(true)//game.isPathEmpty(game.getPath(from, to)))
                    {
                        List<Point> listh = surroundingSquares(from);
                        highlightSurroundingSquares(listh, false);

                        board[to.getRow()][to.getCol()].setPiece(myPiece);
                        board[to.getRow()][to.getCol()].setAttacking(true);

                        board[from.getRow()][from.getCol()].setPiece(null);
                        board[from.getRow()][from.getCol()].setSelected(false);

                        game.move(new Point(from.getRow(), from.getCol()), new Point(to.getRow(), to.getCol()));
                        game.setPhase(1);

                        List<Point> list = surroundingSquares(p);
                        highlightSurroundingSquares(list, true);

                        from = to;
                        to = null;
                    }
                    else
                    {
                        reset();
                    }
                }
                else
                {
                    reset();
                }
            }
            else
            {
                reset();
            }
        }
    }

    private void clickAttack(Point p)
    {
        /*Move move = new Move(from, to);
        updatePiece(new Point(from.getRow(), from.getCol()), null);
        updatePiece(new Point(to.getRow(), to.getRow()), myPiece);
        if(game.isLegalMove(move))
        {
        System.out.println("Arrow: " + p);  

        board[to.getRow()][to.getCol()].setSelected(false);
        board[p.getRow()][p.getCol()].setPiece(Piece.ARROW);

        from = null;
        to = null;

        client.send("move " + move.toString());
        }
        else
        {
        reset();
        }*/
        /*
        System.out.println("test1");
        board[p.getRow()][p.getCol()].setSelected(true);
        game.setPhase(0);*/
        List<Point> list = surroundingSquares(from);
        highlightSurroundingSquares(list, false);
        board[from.getRow()][from.getCol()].setAttacking(false);
        
        from = null;
        
        game.setPhase(0);
        game.setCurrentPlayer(Piece.ENEMY);
    }
}
