public class test {
  public static void main(String[] args) {
	int[][] game = {{2,0,1},
					{1,0,1},
					{0,2,0}};
    Board test = new Board(3,game);
	
	System.out.println(test.rowEq());
	System.out.println(test.colEq());
	System.out.println(test.diagEq());
	System.out.println(test.isGameOver());
	System.out.println(test);
	System.out.println(test.winner());
	System.out.println(test.getReward(1));
	System.out.println(test.getReward(2));
  }
}
