public class Main {
  public static void main(String[] args) {
    String algorithm = args[0];
    boolean withTime = Boolean.parseBoolean(args[1]);
    boolean noOpen = Boolean.parseBoolean(args[2]);

    Game game = new Game(algorithm, withTime, noOpen);
    game.readInputFromFile(args[3]);
    game.play();
  }

}


