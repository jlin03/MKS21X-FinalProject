public class Board {
  private int[][] state;

  public Board(int d) {
    state = new int[d][d];
    clear();
  }
  
  public Board(int d, int[][] load) {
    state = load;
  }
  
  private void clear() {
    for(int r = 0;r < state.length;r++) {
      for(int c = 0;c < state[r].length;c++) {
        state[r][c] = 0;
      }
    }
  }

  public boolean move(int player, int posr, int posc) {
    if((player == 1 || player == 2) && posr >= 0 && posr < 3  && posc >= 0 && posc < 3) {
      if(state[posr][posc] == 0) {
        state[posr][posc] = player;
        return true;
      }
      else {

        return true;
      }
    }
    else {
      return false;
    }
  }

  private boolean isGameOver() {
    boolean over = true;
    for(int r = 0;r < state.length;r++) {
      for(int c = 0;c < state[r].length;c++) {
        if(state[r][c] == 0) {
          over = false;
        }
      }
    }
    return over;
  }

  public int rowEq() {
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

  public int colEq() {
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

  public int diagEq() {
    boolean equal = true;
    for(int r = 0;r < state.length;r++) {
      if(state[0][0] != state[r][r] || state[r][r] == 0) {
        equal = false;
      }
    }
	if(equal) {
        return 1;
	}
	equal = true;
    for(int r = 0;r < state.length;r++) {
      if(state[0][2] != state[r][2-r] || state[r][2-r] == 0) {
        equal = false;
      }
    }
	if(equal) {
        return 2;
    }
    return -1;
  }






}
