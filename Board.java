public class Board {
  private int[][] state;

  public Board(int row, int col) {
    state = new int[row][col];
    clear();
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
      if(state[posr][posc] = 0) {
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





}
