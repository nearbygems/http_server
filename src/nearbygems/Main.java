package nearbygems;

public class Main {

  public static void main(String[] args) {
    new Server((req, resp) -> {
      return "<html><body><h1>Hello, server</h1>It handler</body></html>";
    }).bootstrap();
  }

}
