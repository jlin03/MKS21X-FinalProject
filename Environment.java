import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Environment {
  public Board game;
  public AI p1;
  public AI p2;
  double learnRate = 0.7;
  double discount = 0.2;
  Random rand = new Random((int)System.currentTimeMillis());
  int dim;

  public Environment(int d) {
	game = new Board(d);
	p1 = new AI(d);
	p2 = new AI(d);
	dim = d;
  }

  public Environment(int d, String fileS1, String fileW1, String fileS2, String fileW2) throws Exception {
    game = new Board(d);
    p1 = new AI(d,fileS1,fileW1);
    p2 = new AI(d,fileS2,fileW2);
	dim = d;
  }

  public void learn(boolean inspect) {
	game.clear();
	int turn = 1;
	int n = 1;
	int[] action1 = new int[2];
	int[] nextAction1 = new int[2];
	int[][] prevState1 = new int[dim][dim];
	int[][] currState1 = new int[dim][dim];
	int[] action2 = new int[2];
	int[] nextAction2 = new int[2];
	int[][] prevState2 = new int[dim][dim];
	int[][] currState2 = new int[dim][dim];
	int[][] copy;
	while(!(game.isGameOver())) {
		n = 1;
		prevState1 = game.getState();
		copy = new int[prevState1.length][prevState1.length];
		for(int r = 0; r < prevState1.length; r++) {
			copy[r] = Arrays.copyOf(prevState1[r],prevState1[r].length);
		}
		prevState1 = copy;
		if(rand.nextInt() % 10 > 2) {
			action1 = p1.getNthMaxWeight(prevState1,n);
			while(!(game.isMoveValid(1,action1[0],action1[1]))) {
				p1.changeWeight(prevState1,action1,-1);
				action1 = p1.getNthMaxWeight(prevState1,n);
			}
		}
		else {
			action1[0] = rand.nextInt() % dim;
			action1[1] = rand.nextInt() % dim;
			while(!(game.isMoveValid(1,action1[0],action1[1]))) {
				action1[0] = rand.nextInt() % dim;
				action1[1] = rand.nextInt() % dim;
			}
		}
		game.move(1,action1[0],action1[1]);

		if(inspect) {
			System.out.println(game);
			System.out.println(Arrays.deepToString(p1.getWeights(prevState1)));
			System.out.println(game.gameMessage());
		}

		
		if(game.isGameOver()) {
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) - p1.getWeight(prevState1,action1))));
			p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) - p2.getWeight(prevState2,action2))));
			break;
		}
		else {
			if(!(Arrays.equals(prevState2, currState2))) {
				n = 1;
				currState2 = game.getState();
				nextAction2 = p2.getNthMaxWeight(currState2,n);
				while(!(game.isMoveValid(2,nextAction2[0],nextAction2[1]))) {
					p2.changeWeight(currState2,nextAction2,-1);
					nextAction2 = p2.getNthMaxWeight(currState2,n);
				}
				p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) + (discount * p2.getWeight(currState2,nextAction2)) - p2.getWeight(prevState2,action2))));
			}
		}
	
		
		n = 1;
		prevState2 = game.getState();
		copy = new int[prevState2.length][prevState2.length];
		for(int r = 0; r < prevState2.length; r++) {
			copy[r] = Arrays.copyOf(prevState2[r],prevState2[r].length);
		}
		prevState1 = copy;
		if(rand.nextInt() % 10 > 2) {
			action2 = p2.getNthMaxWeight(prevState2,n);
			while(!(game.isMoveValid(2,action2[0],action2[1]))) {
				p2.changeWeight(prevState2,action2,-1);
				action2 = p2.getNthMaxWeight(prevState2,n);
			}
		}
		else {
			action2[0] = rand.nextInt() % dim;
			action2[1] = rand.nextInt() % dim;
			while(!(game.isMoveValid(2,action2[0],action2[1]))) {
				action2[0] = rand.nextInt() % dim;
				action2[1] = rand.nextInt() % dim;
			}
		}
		game.move(2,action2[0],action2[1]);

		if(inspect) {
			System.out.println(game);
			System.out.println(Arrays.deepToString(p2.getWeights(prevState2)));
			System.out.println(game.gameMessage());
		}
		
		
		if(game.isGameOver()) {
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) - p1.getWeight(prevState1,action1))));
			p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) - p2.getWeight(prevState2,action2))));
			break;
		}
		else {
			n = 1;
			currState1 = game.getState();
			nextAction1 = p1.getNthMaxWeight(currState1,n);
			while(!(game.isMoveValid(1,nextAction1[0],nextAction1[1]))) {
				p1.changeWeight(currState1,nextAction1,-1);
				nextAction1 = p1.getNthMaxWeight(currState1,n);
			}
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) + (discount * p1.getWeight(currState1,nextAction1)) - p1.getWeight(prevState1,action1))));
		}
	}
		
  }

  public void play(int player) {
    game.clear();
    int turn = 1;
    int bot = 0;
    AI opponent;
    int n = 1;
    if(player == 1) {
      bot = 2;
      opponent = p2;
    }
    else {
      bot = 1;
      opponent = p1;
    }

    Scanner move = new Scanner(System.in);
    while(!(game.isGameOver())) {
      if(turn == player) {
        System.out.print("Make your move:");
        String pAction = move.next();
        String[] strAction = pAction.replaceAll("\\s", "").split(",");
        int[] inpAction = new int[2];
        for(int i  = 0; i < 2;i++) {
          inpAction[i] = Integer.parseInt(strAction[i]);
        }
        while(!(game.isMoveValid(player,inpAction[0],inpAction[1]))) {
          System.out.print("Make your move:");
          pAction = move.next();
          strAction = pAction.replaceAll("\\s", "").split(",");
          for(int i  = 0; i < 2;i++) {
            inpAction[i] = Integer.parseInt(strAction[i]);
          }
        }

        game.move(player,inpAction[0],inpAction[1]);
        System.out.println(game);
        System.out.println(game.gameMessage());

        turn = bot;

      }
      else {
        n = 1;
        int[][] prevState = game.getState();
        int[] botAction = opponent.getNthMaxWeight(prevState,n);
        while(!(game.isMoveValid(bot,botAction[0],botAction[1]))) {
          n++;
          botAction = opponent.getNthMaxWeight(prevState,n);
        }

        game.move(bot,botAction[0],botAction[1]);
        System.out.println(game);
        System.out.println(Arrays.deepToString(opponent.getWeights(prevState)));
		System.out.println(game.gameMessage());

        turn = player;

      }

    }
  }


}
