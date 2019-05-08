import mayflower.*;

public class Map
{

    private String[][] grid;

    public Map()
    {
        //2d array to represent a grid on the map
        grid = new String[10][15];
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                grid[r][c] = "block";
            }
        }
    }

    public String[][] getMap()
    {
        return grid;
    }
}

