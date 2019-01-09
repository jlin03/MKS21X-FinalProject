import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
public class Driver {
  public static void main(String[] args) {
	Environment training;
	try {
    if(args.length > 1) {
        if(Integer.isIntInteger(Integer.parseInt(args[0])) && Integer.parseInt(args[0]) > 1) {
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
          Scanner player = new Scanner(System.in);
          int player = move.next();
          if(player == 1 || player == 2) {
            training.play(player);
          }
          else {
            throw new NumberFormatException();
          }
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
