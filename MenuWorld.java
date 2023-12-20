import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MenuWorld {

    private JTextField name;
    private File[] savedGame;
    private JPanel savePanel;

    public MenuWorld(Dresseur dresseur, String playerName) {
        // Créer la fenêtre principale
        JFrame frame = new JFrame("Menu World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);

        // Créer le panneau principal
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // Panel pour les boutons de sauvegarde
        savePanel = new JPanel();
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(savePanel);
        scrollPane.setBounds(10, 120, 250, 150);
        panel.add(scrollPane);

        savedGame = Main.getSavedGame();
        if (savedGame != null) {
            displaySaveButtons(savePanel);
        }

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Étiquette et champ de texte
        JLabel nameLabel = new JLabel("Nom du joueur:");
        nameLabel.setBounds(10, 20, 100, 25);
        panel.add(nameLabel);

        name = new JTextField(20);
        name.setBounds(120, 20, 165, 25);
        panel.add(name);

        // Bouton
        JButton startButton = new JButton("Créer nouveau joueur");
        startButton.setBounds(10, 50, 230, 25);
        panel.add(startButton);

        // Action du bouton
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code à exécuter lorsque le bouton est cliqué
                String playerName = name.getText();
                Dresseur dresseur = new Dresseur(playerName, new ArrayList<Pokemon>());
                dresseur = Main.saveGame(dresseur);

                startHomeWorld(panel, dresseur);
            }
        });
    }

    private void displaySaveButtons(JPanel panel) {
        // Effacer les anciens boutons
        savePanel.removeAll();

        // Ajouter le titre
        JLabel titleLabel = new JLabel("Sauvegardes existantes:");
        savePanel.add(titleLabel);

        for (int i = 0; i < savedGame.length; i++) {
            JButton loadButton = new JButton("Charger " + savedGame[i].getName());
            loadButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            savePanel.add(loadButton);

            final int index = i; // Variable finale nécessaire pour l'accès dans la classe interne

            // Action du bouton de chargement
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Code à exécuter lors du clic sur un bouton de chargement
                    File selectedSave = savedGame[index];
                    System.out.println("Charger la sauvegarde : " + selectedSave.getName());
                    // Ajoutez ici le code pour charger la partie à partir du fichier selectedSave
                    Dresseur dresseur = (Dresseur) Serializer.deserialize(selectedSave.getName());
                    System.out.println(dresseur);
                    startHomeWorld(panel, dresseur);
                }
            });
        }

        // Actualiser le panneau pour refléter les changements
        savePanel.revalidate();
        savePanel.repaint();
    }

    private void startHomeWorld(JPanel panel, Dresseur dresseur) {
        // Close the current window
        JFrame currentWindow = (JFrame) SwingUtilities.getWindowAncestor(panel);
        currentWindow.dispose();

        for (PokemonType t : PokemonType.values()) {
            for (int i = 0; i < 5; i++) {
                dresseur.getRareCandyRandomly(100, t);
            }
        }

        dresseur.addPokemon(Pokedex.getPokemonByName("Pikachu"));

        new HomeWorld(dresseur, "Pokemon by antocreadev & medjourney").setVisible(true);
    }
}
