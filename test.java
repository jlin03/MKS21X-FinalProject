import java.util.Arrays;
public class test {
  public static void main(String[] args) throws Exception{
	int[][] game = {{1,2,1},
					{1,2,1},
					{2,1,2}};
	String tests = "[1, 2, 3];[2, 3, 4];[4, 5, 6]";
    Board test = new Board(3,game);
	
	System.out.println(test.isGameOver());
	System.out.println(test);
	System.out.println(test.winner());
	System.out.println(test.getReward(1));
	System.out.println(test.getReward(2));
	System.out.println(test.gameMessage());
	
	AI testAI = new AI(3,"states.txt","weights.txt");
	System.out.println(testAI.indexOfState(game));
	System.out.println(testAI.getWeights(game));
	System.out.println(testAI.indexOfState(game));
  }
}
