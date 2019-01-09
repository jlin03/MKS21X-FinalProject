import java.util.Arrays;
public class test {
  public static void main(String[] args) throws Exception{
	int[][] game = {{1,2,1},
					{1,2,1},
					{2,1,1}};
	String tests = "[1, 2, 3];[2, 3, 4];[4, 5, 6]";
	int[][] asc = {{1,2,3},{4,5,6},{7,8,9}};
	double[][] weights = {{0.7816086952933228, 0.17269754442801744, 0.12121973511518369}, {0.3324462414019571, 0.5394673927771699, 0.024701116613785157}, {0.3714925195585371, 0.6802480648514232, 0.08136175235757104}};
	Board test = new Board(3,game);
	Environment training = new Environment(3,"p1States.txt","p1Weights.txt","p2States.txt","p2Weights.txt");
	int[] thing = {1,2};
	int one = 0;
	int two = 0;

	System.out.println(test.move(2,2,2));
	System.out.println(test.isGameOver());
	System.out.println(test);
	System.out.println(test.winner());
	System.out.println(test.getReward(1));
	System.out.println(test.getReward(2));
	System.out.println(test.gameMessage());
	
	AI testAI = new AI(3);
	System.out.println(testAI.indexOfState(game));
	System.out.println(testAI.getWeights(game));
	System.out.println(testAI.indexOfState(game));
	System.out.println(testAI.getWeights(asc));
	
	
	
	testAI.export("test1.txt","test2.txt");
	

	training.learn(true);
	for(int i = 0; i < 20000;i++) {
		training.learn(false);
		if(training.game.winner() == 1) {
			one++;
		}
		if(training.game.winner() == 2) {
			two++;
		}
	}
	training.learn(true);
	System.out.println(one);
	System.out.println(two);
	
	training.p1.export("p1States.txt","p1Weights.txt");
	training.p2.export("p2States.txt","p2Weights.txt");
	
	training.play(1);


  }
}
