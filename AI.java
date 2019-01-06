import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class AI {
	private ArrayList<int[][]> states;
	private ArrayList<int[][]> weights;
	
	public AI() {
		states = new ArrayList<int[][]>();
		weights = new ArrayList<int[][]>();
	}
	
	public void export(String fileS, String fileW) throws IOException{
		File state = new File(".", fileS);
		File weight = new File(".", fileW);
		state.createNewFile();
		weight.createNewFile();
	}
	
}