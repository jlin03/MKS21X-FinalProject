import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter; 
import java.io.FileReader; 
import java.io.IOException;
import java.io.FileNotFoundException; 

public class AI {
	private ArrayList<int[][]> states;
	private ArrayList<int[][]> weights;
	int[][] test = {{1,2,3},{2,3,4},{4,5,6}};
	
	public AI() {
		states = new ArrayList<int[][]>();
		weights = new ArrayList<int[][]>();
		for(int i = 0; i < 100; i++) {
			states.add(test);
			weights.add(test);
		}
	}
	
	public void export(String fileS, String fileW) throws Exception{
		File state = new File(fileS);
		File weight = new File(fileW);
		state.createNewFile();
		weight.createNewFile();
		FileWriter copyS = new FileWriter(fileS, false);
		String temp = "";
		for(int i = 0; i < states.size();i++) {
			for(int s = 0; s < states.get(i).length - 1;s++) {
				temp += Arrays.toString(states.get(i)[s]) + ",";
			}
			temp += Arrays.toString(states.get(i)[states.get(i).length-1]);
			if(i != states.size() - 1) {
				temp += System.lineSeparator();
			}
			copyS.write(temp);
			temp = "";
		}
		
		FileWriter copyW = new FileWriter(fileW, false);
		for(int i = 0; i < weights.size();i++) {
			for(int s = 0; s < weights.get(i).length - 1;s++) {
				temp += Arrays.toString(weights.get(i)[s]) + ",";
			}
			temp += Arrays.toString(weights.get(i)[weights.get(i).length-1]);
			if(i != weights.size() - 1) {
				temp += System.lineSeparator();
			}
			copyW.write(temp);
			temp = "";
		}
		
		copyS.close();
		copyW.close();
		
	}
	
}