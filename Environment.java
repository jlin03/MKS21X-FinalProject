import java.util.Arrays;
import java.util.Random;
public class Environment {
  public Board game;
  public AI p1;
  public AI p2;
  double learnRate = 0.7;
  double discount = -0.2;
  Random rand = new Random(56143790);
  
  public Environment(int d) {
	game = new Board(d);
	p1 = new AI(d);
    p2 = new AI(d);
  }

  public Environment(int d, String fileS1, String fileW1, String fileS2, String fileW2) throws Exception {
    game = new Board(d);
    p1 = new AI(d,fileS1,fileW1);
    p2 = new AI(d,fileS2,fileW2);
  }

  public void learn(boolean inspect) {
	game.clear();
	int turn = 1;
	int n = 1;
	int[] action1 = new int[2];
	int[] nextAction1 = new int[2];
	int[][] prevState1 = new int[3][3];
	int[][] currState1 = new int[3][3];
	int[] action2 = new int[2];
	int[] nextAction2 = new int[2];
	int[][] prevState2 = new int[3][3];
	int[][] currState2 = new int[3][3];
	while(!(game.isGameOver())) {
		if(turn == 1) {
			n = 1;
			prevState1 = game.getState();
			if(rand.nextInt() % 10 > 4) {
				action1 = p1.getNthMaxWeight(prevState1,n);
				while(!(game.isMoveValid(1,action1[0],action1[1]))) {
					n++;
					action1 = p1.getNthMaxWeight(prevState1,n);
				}
			}
			else {
				action1[0] = rand.nextInt() % 3;
				action1[1] = rand.nextInt() % 3;
				while(!(game.isMoveValid(1,action1[0],action1[1]))) {
					action1[0] = rand.nextInt() % 3;
					action1[1] = rand.nextInt() % 3;
				}
			}
			game.move(1,action1[0],action1[1]);
			
			if(inspect) {
				System.out.println(game);
				System.out.println(Arrays.deepToString(p1.getWeights(prevState1)));
				System.out.println(game.gameMessage());
			}
			
			if(!(game.isGameOver())) {
				n = 1;
				currState1 = game.getState();
				nextAction1 = p1.getNthMaxWeight(currState1,n);
				while(!(game.isMoveValid(1,nextAction1[0],nextAction1[1]))) {
					n++;
					nextAction1 = p1.getNthMaxWeight(currState1,n);
				}
			}
			
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) + (discount * p1.getWeight(currState1,nextAction1)) - p1.getWeight(prevState1,action1))));
				
			if(game.isGameOver()) {
				p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) + (discount * p2.getWeight(currState2,nextAction2)) - p2.getWeight(prevState2,action2))));
			}
				
			turn = 2;
		}
		else {
			n = 1;
			prevState2 = game.getState();
			if(rand.nextInt() % 10 > 4) {
				action2 = p2.getNthMaxWeight(prevState2,n);
				while(!(game.isMoveValid(2,action2[0],action2[1]))) {
					n++;
					action2 = p2.getNthMaxWeight(prevState2,n);
				}
			}
			else {
				action2[0] = rand.nextInt() % 3;
				action2[1] = rand.nextInt() % 3;
				while(!(game.isMoveValid(2,action2[0],action2[1]))) {
					action2[0] = rand.nextInt() % 3;
					action2[1] = rand.nextInt() % 3;
				}
			}
			game.move(2,action2[0],action2[1]);
			
			if(inspect) {
				System.out.println(game);
				System.out.println(Arrays.deepToString(p2.getWeights(prevState2)));
				System.out.println(game.gameMessage());
			}
			
			if(!(game.isGameOver())) {
				n = 1;
				currState2 = game.getState();
				nextAction2 = p2.getNthMaxWeight(currState2,n);
				while(!(game.isMoveValid(2,nextAction2[0],nextAction2[1]))) {
					n++;
					nextAction2 = p2.getNthMaxWeight(currState2,n);
				}
			}
			
			p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) + (discount * p2.getWeight(currState2,nextAction2)) - p2.getWeight(prevState2,action2))));
			
			if(game.isGameOver()) {
				p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) + (discount * p1.getWeight(currState1,nextAction1)) - p1.getWeight(prevState1,action1))));
			}
			
			turn = 1;
		}
	}
  }


}
