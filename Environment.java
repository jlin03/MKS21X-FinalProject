public class Environment {
  Board game;
  AI p1;
  AI p2;
  int learnRate = 0.7;

  public Environment(int d, String fileS1, String fileW1, String fileS2, String fileW2) {
    game = new Board(d);
    p1 = new AI(fileS1,fileW1);
    p2 = new AI(fileS2,fileW2);
  }

  public int learn() {



  }


  }

}
