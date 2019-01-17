public class Board {
  private int[][] state;

  public Board(int d) {   //initializes a board with dimensions d * d
    state = new int[d][d];
    clear();
  }

  public Board(int d, int[][] load) {   //initializes a board with an already existing array
    state = load;
  }

  public void clear() {   //makes all values on the state array 0
    for(int r = 0;r < state.length;r++) {
      for(int c = 0;c < state[r].length;c++) {
        state[r][c] = 0;
      }
    }
  }

  public boolean move(int player, int posr, int posc) {   //makes a move on the board, returns true if player is 1/2 and the position is a valid point on the state
    if(isMoveValid(player,posr,posc)) {
      state[posr][posc] = player;
      return true;
    }
	return false;
  }

  public boolean isMoveValid(int player, int posr, int posc) {    // same as move(), but doesn't change the board
	  if((player == 1 || player == 2) && posr >= 0 && posr < state.length  && posc >= 0 && posc < state.length) {
		if(state[posr][posc] == 0) {
		  return true;
		}
	  }
	  return false;
  }

  public boolean isGameOver() {   //returns true if the game is a draw or a player has won
    boolean over = true;
    for(int r = 0;r < state.length;r++) {
      for(int c = 0;c < state[r].length;c++) {
        if(state[r][c] == 0) {
          over = false;
        }
      }
    }
	if(rowEq() != -1 || colEq() != -1 || diagEq() != -1) {
		over = true;
	}
    return over;
  }

  private int rowEq() {   //checks all rows and returns the index of a row where a player has all filled, returns -1 if there are no rows with this condition
    boolean equal = true;
    for(int r = 0;r < state.length;r++) {
      equal = true;
      for(int c = 0;c < state[r].length;c++) {
        if(state[r][0] != state[r][c] || state[r][c] == 0) {
          equal = false;
        }
      }
      if(equal) {
        return r;
      }
    }
    return -1;
  }

  private int colEq() {   //same as rowEq() but with cols
    boolean equal = true;
    for(int c = 0;c < state[0].length;c++) {
      equal = true;
      for(int r = 0;r < state.length;r++) {
        if(state[0][c] != state[r][c] || state[r][c] == 0) {
          equal = false;
        }
      }
      if(equal) {
        return c;
      }
    }
    return -1;
  }

  private int diagEq() {    //same as rowEq() but with diags
    boolean equal = true;
    for(int r = 0;r < state.length;r++) {
      if(state[0][0] != state[r][r] || state[r][r] == 0) {
        equal = false;
      }
    }
	if(equal) {
        return 0;
	}
	equal = true;
    for(int r = 0;r < state.length;r++) {
      if(state[0][state.length-1] != state[r][state.length-1-r] || state[r][state.length-1-r] == 0) {
        equal = false;
      }
    }
	if(equal) {
        return (state.length - 1);
    }
    return -1;
  }

  public int winner() {   //returns the winner of the game, 1 or 2, unless there is no winner, then it returns -1
	  if(rowEq() != -1) {
		  return state[rowEq()][0];
	  }
	  if(colEq() != -1) {
		  return state[0][colEq()];
	  }
	  if(diagEq() != -1) {
		  return state[0][diagEq()];
	  }
	  return -1;
  }

  public double getReward(int player) {   //returns -1 for the loser and 1 for the winner, depending on which player wins, returns -0.5 for a draw
	  if(winner() == -1) {
		  return 0;
	  }
	  else {
		  if(winner() == player) {
			  return 1;
		  }
		  if(winner() != player) {
			  return -1;
		  }
	  }
	  return 0;
  }

  public int[][] getState() {   //returns the state array
	  return state;
  }

  public String gameMessage() {   //prints the current status of the game
	  if(isGameOver()) {
		  if(winner() == -1) {
			  return "The game has been completed. The result is a draw.";
		  }
		  else {
			  return "The game has been completed. The winner is player  " + winner();
		  }
	  }
	  return "The game is in progress...";
  }

  private String convert(int input) {   //converts 1 and 2 to X and O
	if(input == 1) {
	  return "X";
	}
	if(input == 2) {
	  return "O";
	}
	if(input == 0) {
	  return " ";
	}
	return null;
  }


  public String toString() {    //prints the board with the current state of the game
	String out = "";
	for(int r = 0;r < state.length-1;r++) {
		for(int c = 0; c < state[r].length - 1;c++) {
		  out += convert(state[r][c]);
		  out += "|";
		}
		out += convert(state[r][state[r].length-1]);
    out += "\n";
    for(int i = 0;i < state.length;i++) {
		  out += "- ";
    }
    out += "\n";
	}
	for(int c = 0; c < state[state.length-1].length - 1;c++) {
		out += convert(state[state.length-1][c]);
		out += "|";
	}
	out += convert(state[state.length-1][state[state.length-1].length-1]);
	return out;
  }






}
