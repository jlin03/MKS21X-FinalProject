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
	private int[][] empty;
	
	public AI(int d) {
		states = new ArrayList<int[][]>();
		weights = new ArrayList<int[][]>();
		empty = new int[d][d];
	}
	
	private void addState(int[][] state) {
		states.add(state);
	}
	
	private void addWeight(int[][] weight) {
		weights.add(weight);
	}
	
	public void changeWeight(int[][] state, int[] weight, int val) {
		if(indexOfState(state) != -1) {
			weights.get(indexOfState(state))[weight[0]][weight[1]] = val;
		}
		else {
			addState(state);
			addWeight(empty);
			changeWeight(state,weight,val);
		}
	}
	
	public int indexOfState(int[][] state) {
		for(int i = 0; i < states.size();i++) {
			if(Arrays.deepEquals(state,states.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	public int[][] getWeights(int[][] state) {
		if(indexOfState(state) == -1) {
			addState(state);
			addWeight(empty);
		}
		return weights.get(indexOfState(state));
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