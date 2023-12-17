import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 3003;

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }

    public void startClient() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connecté au serveur sur le port " + SERVER_PORT);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Envoyer un objet au serveur
            Object clientObject = new ClientRequest("Demande du client");
            objectOutputStream.writeObject(clientObject);

            // Lire la réponse du serveur
            Object serverResponse = objectInputStream.readObject();
            System.out.println("Réponse du serveur : " + serverResponse);

            // Ne pas fermer la connexion ici pour permettre une communication continue avec le serveur
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Exemple d'objet de demande du client
    private static class ClientRequest implements java.io.Serializable {
        private String message;

        public ClientRequest(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ClientRequest{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
