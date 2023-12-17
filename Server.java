import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 3003;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion cliente : " + clientSocket.getInetAddress().getHostAddress());

                // Traitement du client dans un thread séparé
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                // Lire l'objet du client
                Object clientObject = objectInputStream.readObject();
                System.out.println("Objet reçu du client : " + clientObject);

                // Envoyer un objet en réponse
                Object serverResponse = new ServerResponse("Réponse du serveur à votre objet");
                objectOutputStream.writeObject(serverResponse);

                // Ne pas fermer la connexion ici pour permettre une communication continue avec le client
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Exemple d'objet de réponse du serveur
    private static class ServerResponse implements java.io.Serializable {
        private String message;

        public ServerResponse(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ServerResponse{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
