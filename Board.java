public class Board {
  private int[][] state;

  public Board(int d) {
    state = new int[d][d];
    clear();
  }

  public Board(int d, int[][] load) {
    state = load;
  }

  public void clear() {
    for(int r = 0;r < state.length;r++) {
      for(int c = 0;c < state[r].length;c++) {
        state[r][c] = 0;
      }
    }
  }

  public boolean move(int player, int posr, int posc) {
    if(isMoveValid(player,posr,posc)) {
      state[posr][posc] = player;
      return true;
    }
	return false;
  }

  public boolean isMoveValid(int player, int posr, int posc) {
	  if((player == 1 || player == 2) && posr >= 0 && posr < state.length  && posc >= 0 && posc < state.length) {
		if(state[posr][posc] == 0) {
		  return true;
		}
	  }
	  return false;
  }

  public boolean isGameOver() {
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

  private int rowEq() {
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

  private int colEq() {
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

  private int diagEq() {
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

  public int winner() {
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

  public double getReward(int player) {
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
	  return -0.5;
  }

  public int[][] getState() {
	  return state;
  }

  public String gameMessage() {
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

  private String convert(int input) {
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


  public String toString() {
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
