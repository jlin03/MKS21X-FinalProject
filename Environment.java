import java.util.Arrays;
public class Environment {
  Board game;
  AI p1;
  AI p2;
  double learnRate = 0.7;

  public Environment(int d, String fileS1, String fileW1, String fileS2, String fileW2) throws Exception {
    game = new Board(d);
    p1 = new AI(d,fileS1,fileW1);
    p2 = new AI(d,fileS2,fileW2);
  }

  public void learn(boolean inspect) {
	game.clear();
	int turn = 1;
	int n = 1;
	int[] action;
	int[] nextAction;
	int[][] prevState;
	int[][] currState;
	while(!(game.isGameOver())) {
		if(turn == 1) {
			n = 1;
			prevState = game.getState();
			action = p1.getNthMaxWeight(prevState,n);
			while(!(game.isMoveValid(1,action[0],action[1]))) {
				n++;
				action = p1.getNthMaxWeight(prevState,n);
			}
			game.move(1,action[0],action[1]);
			
			if(inspect) {
				System.out.println(game);
				System.out.println(Arrays.deepToString(p1.getWeights(prevState)));
				System.out.println(game.gameMessage());
			}
			
			n = 1;
			currState = game.getState();
			nextAction = p1.getNthMaxWeight(currState,n);
			while(!(game.isMoveValid(1,action[0],action[1]))) {
				n++;
				action = p1.getNthMaxWeight(currState,n);
			}
			
			p1.changeWeight(prevState,action,p1.getWeight(prevState,action) + (learnRate * (game.getReward(1) + p1.getWeight(currState,nextAction) - p1.getWeight(prevState,action))));
				
		}
		if(turn == 2) {
			
		
		}
		
	}


  }



}
