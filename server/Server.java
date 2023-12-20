package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

  public static final int PORT = 6942;
  private ServerSocket server;
  private ExecutorService pool = Executors.newFixedThreadPool(2);

  public static ArrayList<ClientHandler> connections = new ArrayList<ClientHandler>();

  @Override
  public void run() {
    try {
      server = new ServerSocket(PORT);
      System.out.println("Server is running on port " + PORT);

      while (true) {
        Socket client = server.accept();
        ClientHandler clientThread = new ClientHandler(client);
        connections.add(clientThread);
        pool.execute(clientThread);
      }

    } catch (Exception e) {
      shutdown();
    }
  }

  public void shutdown() {
    try {
      if (!server.isClosed()) {
        server.close();
      }
      for (ClientHandler ch : connections) {
        ch.broadcast(new ObjectStream(null, "Server is shutting down"));
        if (ch != null) {
          ch.disconnect();
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public static void log(Object o) {
    System.out.println("[LOG] > " + o);
  }

  public static void log(Object o, String prefix) {
    System.out.println("[" + prefix + "] > " + o);
  }

  public static enum Commands {
    INIT_TRAINER("/init_trainer"),
    IN_QUEUE("/in_queue"),
    BATTLE_STARTING("/battle_starting"),
    BATTLE_END("/battle_end"),
    SET_ACTION("/set_action"),
    EOT("/eot"),
    NEXT_TURN("/next_turn"),

    ATTACK("/attack"),
    HEAL("/heal"),
    QUIT("/quit");

    private final String cmd;

    Commands(String cmd) {
      this.cmd = cmd;
    }

    public String getCmd() {
      return cmd;
    }
  }

  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

}
