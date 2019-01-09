import java.io.IOException;
import java.io.FileNotFoundException;
public class Driver {
  public static void main(String[] args) {
	Environment training;
	try {
		training = new Environment(3,"p1States.txt","p1Weights.txt","p2States.txt","p2Weights.txt");
		training.play(1);
		training.play(2);
		training.play(1);
		training.play(2);
		training.play(1);
	}
	catch (Exception e) {
		System.out.println("File(s) not found");
	}
	
	
  }

}
