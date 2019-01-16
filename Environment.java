import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Environment {
  public Board game;    //tic tac toe board
  public AI p1;         //player1(X)
  public AI p2;         //player2(O)
  double learnRate = 0.7;   //constant used for q-learning algorithm
  double discount = 0.6;    //constant used for q-learning algorithm
  Random rand = new Random((int)System.currentTimeMillis());    //randomizer for exploratory moves
  int dim;

  public Environment(int d) {   //initializes the environment with no data
	game = new Board(d);
	p1 = new AI(d);
	p2 = new AI(d);
	dim = d;
  }

  public Environment(int d, String fileS1, String fileW1, String fileS2, String fileW2) throws Exception {    //initializes environment with file data
    game = new Board(d);
    p1 = new AI(d,fileS1,fileW1);
    p2 = new AI(d,fileS2,fileW2);
	dim = d;
  }

  public void learn(boolean inspect, boolean explore) {    //inspect boolean prints the game as it is played if set true, if explore is true, bots may make random moves.
	game.clear();
  int r;
  if(explore) {
    r = 2;
  }
  else {
    r = 0;
  }
	int turn = 1;
	int n = 1;
	int[] action1 = new int[2];              //current action(move) for player1
	int[] nextAction1 = new int[2];          //future action(move) for player1
	int[][] prevState1 = new int[dim][dim];  //state of the board before current action1
	int[][] currState1 = new int[dim][dim];  //state of the board after current action1
	int[] action2 = new int[2];
	int[] nextAction2 = new int[2];
	int[][] prevState2 = new int[dim][dim];
	int[][] currState2 = new int[dim][dim];
	nextAction1 = p1.getNthMaxWeight(prevState1,n);  //next action is set to the maximum weight value's index as default
	while(!(game.isGameOver())) {    //if the game is still ongoing
		n = 1;
		prevState1 = copy(game.getState());   //copy the prevState
		action1 = Arrays.copyOf(nextAction1,2);   //make the current action the nextAction
		while(!(game.isMoveValid(1,action1[0],action1[1]))) {   //if the move is not valid, make the weight at that index -1, and search for next max
			p1.changeWeight(prevState1,action1,-1);
			action1 = p1.getNthMaxWeight(prevState1,n);
		}

		game.move(1,action1[0],action1[1]);   //make the move on the board for player 1

		if(inspect) {   //prints the game if inspect is true
			System.out.println(game);
			p1.printWeights(p1.getWeights(prevState1));
			System.out.println(game.gameMessage());
		}


		if(game.isGameOver()) {   //if the game is over, update both players' weights
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) - p1.getWeight(prevState1,action1))));
			p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) - p2.getWeight(prevState2,action2))));
			break;
		}
		else {    //else choose the nextAction (20% exploratory 80% optimal), then update the weights for player 2
			n = 1;
			currState2 = copy(game.getState());
			if(Math.abs(rand.nextInt() % 10) >= r) {
				nextAction2 = p2.getNthMaxWeight(currState2,n);
				while(!(game.isMoveValid(2,nextAction2[0],nextAction2[1]))) {
					p2.changeWeight(currState2,nextAction2,-1);
					nextAction2 = p2.getNthMaxWeight(currState2,n);
				}
			}
			else {
				nextAction2[0] = Math.abs(rand.nextInt() % dim);
				nextAction2[1] = Math.abs(rand.nextInt() % dim);
				while(!(game.isMoveValid(1,nextAction2[0],nextAction2[1]))) {
					p2.changeWeight(currState2,nextAction2,-1);
					nextAction2[0] = Math.abs(rand.nextInt() % dim);
					nextAction2[1] = Math.abs(rand.nextInt() % dim);
				}
			}
			p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) + (discount * p2.getWeight(currState2,p2.getNthMaxWeight(currState2,n))) - p2.getWeight(prevState2,action2))));
		}


		n = 1;      //exact same code as the last block, but with player1 and player2 switched
		prevState2 = copy(game.getState());
		action2 = Arrays.copyOf(nextAction2,2);
		while(!(game.isMoveValid(2,action2[0],action2[1]))) {
			p2.changeWeight(prevState2,action2,-1);
			action2 = p2.getNthMaxWeight(prevState2,n);
		}

		game.move(2,action2[0],action2[1]);

		if(inspect) {
			System.out.println(game);
			p2.printWeights(p2.getWeights(prevState2));
			System.out.println(game.gameMessage());
		}


		if(game.isGameOver()) {
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) - p1.getWeight(prevState1,action1))));
			p2.changeWeight(prevState2,action2,p2.getWeight(prevState2,action2) + (learnRate * (game.getReward(2) - p2.getWeight(prevState2,action2))));
			break;
		}
		else {
			n = 1;
			currState1 = copy(game.getState());
			if(Math.abs(rand.nextInt() % 10) >= r) {
				nextAction1 = p1.getNthMaxWeight(currState1,n);
				while(!(game.isMoveValid(1,nextAction1[0],nextAction1[1]))) {
					p1.changeWeight(currState1,nextAction1,-1);
					nextAction1 = p1.getNthMaxWeight(currState1,n);
				}
			}
			else {
				nextAction1[0] = Math.abs(rand.nextInt() % dim);
				nextAction1[1] = Math.abs(rand.nextInt() % dim);
				while(!(game.isMoveValid(1,nextAction1[0],nextAction1[1]))) {
					p1.changeWeight(currState1,nextAction1,-1);
					nextAction1[0] = Math.abs(rand.nextInt() % dim);
					nextAction1[1] = Math.abs(rand.nextInt() % dim);
				}
			}
			p1.changeWeight(prevState1,action1,p1.getWeight(prevState1,action1) + (learnRate * (game.getReward(1) + (discount * p1.getWeight(currState1,p1.getNthMaxWeight(currState1,n))) - p1.getWeight(prevState1,action1))));
		}
	}

  }

  public void play(int player) {    //starts a game as a certain player(user vs AI)
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

    System.out.println(game);

    Scanner move = new Scanner(System.in);
    while(!(game.isGameOver())) {   //if the game is ongoing
      if(turn == player) {    //if it is your turn, user is prompted to make a move, which is put on the board unless the input is invalid
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
      else {    //the AI chooses the optimal move 100% of the time and puts it on the board
        n = 1;
        int[][] prevState = copy(game.getState());
        int[] botAction = opponent.getNthMaxWeight(prevState,n);
        while(!(game.isMoveValid(bot,botAction[0],botAction[1]))) {
          n++;
          botAction = opponent.getNthMaxWeight(prevState,n);
        }

        game.move(bot,botAction[0],botAction[1]);
        System.out.println(game);
        opponent.printWeights(opponent.getWeights(prevState));
		System.out.println(game.gameMessage());

        turn = player;

      }

    }
  }

  public int[][] copy(int[][] input) {    //copies an int[][] array to store as data and dissociate from reference
	int[][] copy = new int[input.length][input.length];
	for(int r = 0; r < input.length; r++) {
		copy[r] = Arrays.copyOf(input[r],input[r].length);
	}
	return copy;
  }


}
