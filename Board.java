public class Board {
  private int[][] state;

  public Board() {
    clear();
  }

  private void clear() {
    for(int r = 0;r < state.length;r++) {
      for(int c = 0;c < state[r].length;c++) {
        state[r][c] = 0;
      }
    }
  }
  




}
