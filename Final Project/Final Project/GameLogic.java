import java.util.List;
import java.util.LinkedList;

public class GameLogic
{
    //INSTANCE VARIABLES
    private Piece[][] board;
    private Piece currentPlayer;
    private int phase;

    //CONSTRUCTORS
    public GameLogic()
    {
        //initialize instance variables
        board = new Piece[10][15];
        currentPlayer = Piece.FRIENDLY;
        //setup the board to its initial state
        board[3][0] = Piece.FRIENDLY;
        board[6][0] = Piece.FRIENDLY;
        board[5][14] = Piece.ENEMY;
        phase = 0;
    }

    //METHODS
    /**
     *  Return the current turn holder's Piece, Piece.BLACK or Piece.WHITE
     */
    public Piece getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     *  Setter for currentPlayer instance variable
     */
    public void setCurrentPlayer(Piece piece)
    {
        currentPlayer = piece;
    }
    
    public int getPhase()
    {
        return phase;
    }
    
    public void setPhase(int num)
    {
        phase = num;
    }

    /**
     *  Change the currentPlayer
     *      Piece.BLACK -> Piece.WHITE
     *      Piece.WHITE -> Piece.BLACK
     */
    public void nextPlayer()
    {
        if(currentPlayer.equals(Piece.ENEMY))
        {
            currentPlayer = Piece.FRIENDLY;
        }
        else
        {
            currentPlayer = Piece.ENEMY;
        }
    }

    /**
     *  Return the piece that is on the board at the specified Point
     *      Piece.BLACK, Piece.WHITE, or Piece.ARROW
     */
    public Piece getPiece(Point p)
    {
        return board[p.getRow()][p.getCol()];
    }

    public void setPiece(Piece piece, Point p)
    {
        board[p.getRow()][p.getCol()] = piece;
    }

    /**
     *  Move the piece located at Point from to Point to
     */
    public void move(Point from, Point to)
    {
        board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()] = null;
    }

    /**
     *  Return true if there is no piece at Point p
     */
    public boolean isEmpty(Point p)
    {
        if(board[p.getRow()][p.getCol()] != null)
            return false;
        return true;
    }

    /**
     *  Return a List of Points that form a straight line between Point from and Point to
     *  This line can be horizontal, vertical, or diagonal
     *  If there is no straight line between from and to, return an empty List
     */
    public List<Point> getPath(Point from, Point to)
    {
        List<Point> list = new LinkedList<Point>();
        if(from.equals(to))
        {
            list.add(from);
            return list;
        }
        if(from.getRow() == to.getRow())
        {
            if(from.getCol() < to.getCol()) //left to right
            {
                for(int i = from.getCol(); i <= to.getCol(); i++)
                {
                    list.add(new Point(from.getRow(), i));
                }
            }
            else //right to left
            {
                for(int i = from.getCol(); i >= to.getCol(); i--)
                {
                    list.add(new Point(from.getRow(), i));
                } 
            }
        }
        else if(from.getCol() == to.getCol())
        {
            if(from.getRow() < to.getRow()) //top to bottom
            {
                for(int i = from.getRow(); i <= to.getRow(); i++)
                {
                    list.add(new Point(i, from.getCol()));
                }
            }
            else //bottom to top
            {
                for(int i = from.getRow(); i >= to.getRow(); i--)
                {
                    list.add(new Point(i, from.getCol()));
                } 
            }
        }
        else if(Math.abs(from.getRow()-to.getRow()) == Math.abs(from.getCol()-to.getCol()))
        {
            if(from.getRow() < to.getRow())
            {
                if(from.getCol() < to.getCol()) //down and right
                {
                    for(int i = from.getRow(); i <= to.getRow(); i++)
                    {
                        list.add(new Point(i, from.getCol()+(i - from.getRow())));
                    }
                }
                else //down and left
                {
                    for(int i = from.getRow(); i <= to.getRow(); i++)
                    {
                        list.add(new Point(i, from.getCol()-(i - from.getRow())));
                    }
                }
            }
            else
            {
                if(from.getCol() < to.getCol()) //up and right
                {
                    for(int i = from.getRow(); i >= to.getRow(); i--)
                    {
                        list.add(new Point(i, from.getCol()-(i - from.getRow())));
                    }
                }
                else //up and left
                {
                    for(int i = from.getRow(); i >= to.getRow(); i--)
                    {
                        list.add(new Point(i, from.getCol()+(i - from.getRow())));
                    }
                }
            }
        }
        return list;
    }

    /**
     *  Return true if all of the Points on path are empty EXCLUDING the first Point
     *  Return false if the path is size 1
     *  Return false if path is the empty List
     */
    public boolean isPathEmpty(List<Point> path)
    {
        if(path.size() == 1)
        {
            return false;
        }
        if(path.isEmpty())
        {
            return false;
        }
        for(int i = 1; i < path.size(); i++)
        {
            if(!isEmpty(path.get(i)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     *  Return a List of the Points that contain pieces that match the specified piece (Piece.BLACK, Piece.WHITE)
     */
    public List<Point> getPieces(Piece piece)
    {
        List<Point> list = new LinkedList<Point>();
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[r].length; c++)
            {
                if(board[r][c] != null && board[r][c].equals(piece))
                {
                    list.add(new Point(r, c));
                }
            }
        }
        return list;
    }

    /**
     *  Return a List of Moves that are legal for the point located at Point from
     *  Return the empty List if there is no piece at Point from or if the Piece is an arrow
     *  Do not worry about the currentPlayer.
     */
    public List<Move> getLegalMoves(Point from)
    {
        List<Move> list = new LinkedList<Move>();
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[r].length; c++)
            {

                Move move = new Move(from, new Point(r, c));
                if(isLegalMove(move))
                {
                    list.add(move);
                }

            }
        }
        return list;
    }

    /**
     *  Return a List of Moves that are legal for pieces that match the specified piece (Piece.BLACK, Piece.WHITE)
     *  Do not worry about the currentPlayer
     *  Return the empty List if piece is an arrow
     */

    public List<Move> getLegalMoves(Piece piece)
    {
        List<Point> pieceList = getPieces(piece);
        List<Move> ret = new LinkedList<Move>();
        for(int i = 0; i < pieceList.size(); i++)
        {
            List<Move> list = getLegalMoves(pieceList.get(i));
            for(int t = 0; t < list.size(); t++)
            {
                ret.add(list.get(t));
            }
        }
        return ret;
    }

    /**
     *  Return true if the specified Move is a legal move
     *  Do not worry about the currentPlayer
     */

    public boolean isLegalMove(Move move)
    {
        /*Point from = move.getPiece();
        if(from == null || board[from.getRow()][from.getCol()] == null)
        {
        return false;
        }
        if(board[from.getRow()][from.getCol()].equals(Piece.BLACK) || board[from.getRow()][from.getCol()].equals(Piece.WHITE))
        {
        if(!move.getPiece().equals(move.getDestination()))
        {
        if(isPathEmpty(getPath(move.getPiece(), move.getDestination())))
        {
        Piece temp = getPiece(move.getPiece());
        setPiece(null, move.getPiece());
        List<Point> path = getPath(move.getDestination(), move.getArrow());
        if(isPathEmpty(path))
        {
        setPiece(temp, move.getPiece());
        return true;
        }
        setPiece(temp, move.getPiece());
        }
        }
        }                
        return false;*/
        return true;
    }

    /**
     *  Return true if the piece at the specified Point has any legal moves
     *  Return false if there is no piece at the specified Point, or if that piece is an arrow
     *  Do not worry about the currentPlayer
     */
    /*
    public boolean hasMoves(Point piecePosition)
    {
    if(board[piecePosition.getRow()][piecePosition.getCol()] == null || board[piecePosition.getRow()][piecePosition.getCol()] == Piece.ARROW)
    {
    return false;
    }
    if(getLegalMoves(piecePosition).size() > 0)
    {
    return true;
    }
    return false;
    }*/

    /**
     *  If the game is over, return the piece that is the winner
     *  The winner is the piece that still has legal moves left
     *  If neither piece has any legal moves, return null
     *  If the game is not over, return null
     */
    /*
    public Piece getWinner()
    {
    if(isGameOver())
    {
    if(getLegalMoves(Piece.BLACK).size() == 0)
    {
    return Piece.WHITE;
    }
    else
    {
    return Piece.BLACK;
    }
    }
    return null;
    }*/

    /**
     *  Return true if at least one of the pieces (Piece.BLACK, Piece.WHITE) has 0 legal moves left
     */

    /*public boolean isGameOver()
    {
    if(getLegalMoves(Piece.BLACK).size() == 0 || getLegalMoves(Piece.WHITE).size() == 0)
    {
    return true;
    }
    return false;
    }*/

}
