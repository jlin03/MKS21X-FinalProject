import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AI {
	private ArrayList<int[][]> states;			//ArrayList holding all the states the AI has seen
	private ArrayList<double[][]> weights;	//ArrayList holding all the weights in relation to each state
	private double[][] empty;								//empty double for tracking the dimension of the board
	Random rand =  new Random((int)System.currentTimeMillis());				//random generator when making random weights

	public AI(int d) {		//initializes AI to play under a d * d board
		states = new ArrayList<int[][]>();
		weights = new ArrayList<double[][]>();
		empty = new double[d][d];
	}

	public AI(int d, String fileS, String fileW) throws Exception {		//initializes AI to play under a d * d board, but with pre-determined states/weights
		states = new ArrayList<int[][]>();
		weights = new ArrayList<double[][]>();
		empty = new double[d][d];
		importF(fileS,fileW);
	}

	public void addState(int[][] state) {		//adds a copy of a state into the states ArrayList
		int[][] copy = new int[state.length][state.length];
		for(int r = 0; r < state.length; r++) {
			copy[r] = Arrays.copyOf(state[r],state[r].length);
		}
		states.add(copy);
	}

	public void addWeight(double[][] weight) {		//adds a copy of a weight into the weights ArrayList
		double[][] copy = new double[weight.length][weight.length];
		for(int r = 0; r < weight.length; r++) {
			copy[r] = Arrays.copyOf(weight[r],weight[r].length);
		}
		weights.add(copy);
	}

	public int indexOfState(int[][] state) {		//gets the index of a specific state in the states ArrayList
		for(int i = 0; i < states.size();i++) {
			if(Arrays.deepEquals(state,states.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public void changeWeight(int[][] state, int[] weight, double val) {		//sets the weight in a certain position in relation to the state
		if(indexOfState(state) != -1) {
			weights.get(indexOfState(state))[weight[0]][weight[1]] = val;
		}
		else {
			addState(state);
			addWeight(scrambledWeights());
			changeWeight(state,weight,val);
		}
	}

	public double getWeight(int[][] state, int[] weight) {		//returns the weight value at a certain position in relation to the state
		if(indexOfState(state) == -1) {
			addState(state);
			addWeight(scrambledWeights());
		}
		return weights.get(indexOfState(state))[weight[0]][weight[1]];
	}

	public double[][] getWeights(int[][] state) {		//returns the entire double[][] weight array in relation to the state
		if(indexOfState(state) == -1) {
			addState(state);
			addWeight(scrambledWeights());
		}
		return weights.get(indexOfState(state));
	}

	public int[] getNthMaxWeight(int[][] state, int n) {		//returns the index of the maximum weight given a state
		if(indexOfState(state) == -1) {
			addState(state);
			addWeight(scrambledWeights());
		}
		return getNthMax(weights.get(indexOfState(state)),n);
	}

	public int[] getNthMax(double[][] array, int n) {		//returns the nth max value of a double array
		double[] sorted = sortArray(array);
		double nth;
		if (n > sorted.length) {
			nth = sorted.length - 1;
		}
		else {
			nth = sorted[sorted.length-n];
		}
		int[] max = {0,0};
		for(int r = 0; r < array.length;r++) {
			for(int c = 0; c < array[r].length;c++) {
				if(Double.compare(nth,array[r][c]) == 0) {
					max[0] = r;
					max[1] = c;
					return max;
				}
			}
		}
		return max;
	}

	public double[] sortArray(double[][] array) {		//sorts an array for finding the max
		double[] newArray = new double[array.length*array.length];
		int n = 0;
		for(int r = 0; r < array.length;r++) {
			for(int c = 0; c < array[r].length;c++) {
				newArray[n] = array[r][c];
				n++;
			}
		}
		Arrays.sort(newArray);
		return newArray;

	}

	public double[][] scrambledWeights() {		//returns a randomly generated weight array
		double[][] output = new double[empty.length][empty.length];
		for(int r = 0; r < output.length;r++) {
			for(int c = 0; c < output[r].length;c++) {
				output[r][c] = rand.nextDouble() * 0.1;
			}
		}
		return output;
	}

	public void printWeights(double[][] weight) {		//prints a double array in rows
		for(int i = 0; i < weight.length;i++) {
			System.out.println(Arrays.toString(weight[i]));
		}
	}

	public double[][] stringToDoubleArray(String array) {		//converts string to a double array
		String[] split1 = array.split(";");
		String[][] split2 = new String[split1.length][split1.length];
		double[][] output = new double[split1.length][split1.length];
		for(int i = 0; i < split1.length;i++) {
			split2[i] = split1[i].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
		}
		for(int r = 0; r < split2.length;r++) {
			for(int c = 0; c < split2[r].length;c++) {
				output[r][c] = Double.parseDouble(split2[r][c]);
			}
		}
		return output;
	}

	public int[][] stringToIntArray(String array) {		//converts a string to an int array
		String[] split1 = array.split(";");
		String[][] split2 = new String[split1.length][split1.length];
		int[][] output = new int[split1.length][split1.length];
		for(int i = 0; i < split1.length;i++) {
			split2[i] = split1[i].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
		}
		for(int r = 0; r < split2.length;r++) {
			for(int c = 0; c < split2[r].length;c++) {
				output[r][c] = Integer.parseInt(split2[r][c]);
			}
		}
		return output;
	}

	public void importF(String fileS, String fileW) throws Exception {		//imports 2 files for states/weights ArrayLists
		File state = new File(fileS);
		File weight = new File(fileW);
		state.createNewFile();
		weight.createNewFile();
		Scanner impS = new Scanner(state);
		String temp = "";
		while(impS.hasNext()) {
			temp = impS.nextLine();
			addState(stringToIntArray(temp));
		}
		Scanner impW = new Scanner(weight);
		while(impW.hasNext()) {
			temp = impW.nextLine();
			addWeight(stringToDoubleArray(temp));
		}
		impS.close();
		impW.close();
	}

	public void export(String fileS, String fileW) throws Exception {		//exports states/weights ArrayLists to files
		File state = new File(fileS);
		File weight = new File(fileW);
		state.createNewFile();
		weight.createNewFile();
		FileWriter copyS = new FileWriter(fileS, false);
		String temp = "";
		for(int i = 0; i < states.size();i++) {
			for(int s = 0; s < states.get(i).length - 1;s++) {
				temp += Arrays.toString(states.get(i)[s]) + ";";
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
				temp += Arrays.toString(weights.get(i)[s]) + ";";
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
