import mayflower.*;
public class Runner 
{
    public static void main(String[] args) 
    {
    	new Mayflower("Game", 1080, 720, new MyStage(new GameLogic()));
    }    
}