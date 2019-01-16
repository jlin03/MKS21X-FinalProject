import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
public class Driver {
  public static void main(String[] args) {
	Environment training;
	Scanner input = new Scanner(System.in);
	try {
    if(args.length > 1) {
        if(Integer.parseInt(args[0]) > 1) {
          if(args.length >= 6) {
            training = new Environment(Integer.parseInt(args[0]),args[2],args[3],args[4],args[5]);
          }
          else {
            training = new Environment(Integer.parseInt(args[0]),"p1States.txt","p1Weights.txt","p2States.txt","p2Weights.txt");
          }
        }
        else {
          throw new NumberFormatException();
        }

        if(args[1].equals("play")) {
          boolean playagain = true;
          while(playagain) {
		          System.out.println("Start as player 1 or 2?:");
              int p = input.nextInt();
              if(p == 1 || p == 2) {
                training.play(p);
              }
              else {
                throw new NumberFormatException();
              }
              System.out.println("Play again?[y/n]");
              String plyag = input.next();
              if(plyag.equals("n")) {
        				playagain = false;
        			}
              else {
        				if(!(plyag.equals("y"))) {
        					throw new Exception();
        				}
        			}
          }
        }

		if(args[1].equals("train")) {
			int win1 = 0;
			int win2 = 0;
			int draw = 0;
			boolean inspect = false;
      boolean explore = false;
      System.out.println("Should the bots make random moves?[y/n] ");
			String rnd = input.next();
			System.out.println("How many games should be played? ");
			int n = Math.abs(input.nextInt());
			System.out.println("Should the games be printed?[y/n] ");
			String yn = input.next();
			if(yn.equals("y")) {
				inspect = true;
			}
			else {
				if(!(yn.equals("n"))) {
					throw new Exception();
				}
			}
      if(rnd.equals("y")) {
				explore = true;
			}
			else {
				if(!(rnd.equals("n"))) {
					throw new Exception();
				}
			}

			for(int i = 0; i < n; i++) {
				training.learn(inspect,explore);
				if(training.game.winner() == 1) {
					win1++;
				}
				if(training.game.winner() == 2) {
					win2++;
				}
				if(training.game.winner() == -1) {
					draw++;
				}
			}
			System.out.println("Summary:\nPlayer 1 win count: " + win1 + "\nPlayer 2 win count: " + win2+ "\nDraw count: " + draw);

			if(args.length >= 6) {
				training.p1.export(args[2],args[3]);
				training.p2.export(args[4],args[5]);
			}
			else {
				training.p1.export("p1States.txt","p1Weights.txt");
				training.p2.export("p2States.txt","p2Weights.txt");
			}
		}

		if(!(args[1].equals("train")) && !(args[1].equals("play"))) {
			throw new Exception();
		}

    }
    else {
      throw new Exception();
    }

	}
	catch (Exception e) {
		System.out.println("Inputs invalid or files not found.");
	}


  }

}
