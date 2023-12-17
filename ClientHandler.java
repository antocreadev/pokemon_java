import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Obtenez les flux d'entrée/sortie pour la communication avec le client
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Exemple : Envoyer un message au client
            String message = "Bienvenue sur le serveur!";
            outputStream.write(message.getBytes());

            // Vous pouvez ajouter ici la logique de gestion de la communication avec le client.

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fermer la connexion client une fois la communication terminée
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
      
    }
}
