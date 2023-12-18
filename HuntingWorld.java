import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HuntingWorld extends World {

    private Image wildPokemonImage;
    private Pokemon wildPokemon;
    private int resultCatch = -2;
    private boolean catchHandled = false;
    public HuntingWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        wildPokemon = Pokemon.getRandomPokemon();
        wildPokemonImage = new ImageIcon("assets/img/pokemons/front/" + wildPokemon.getId() + ".png").getImage();
        backgroundImage = new ImageIcon("assets/img/decors/wallHunterWorld.png").getImage();
        loadBackgroundMusic("assets/sounds/fight.wav");
    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        g.drawImage(wildPokemonImage, screenWidth / 4, screenHeight / 8, screenWidth / 2, screenHeight / 2, this);

        g.setColor(Color.WHITE);
        String label = "(y) Attraper le Pokemon ?";
        int labelWidth = g.getFontMetrics().stringWidth(label);
        g.drawString(label, screenWidth / 2 - labelWidth / 2, screenHeight - 100);

        g.setColor(Color.WHITE);
        String label2 = "(n) Fuite ?";
        int label2Width = g.getFontMetrics().stringWidth(label);
        g.drawString(label2, screenWidth / 2 - label2Width / 2, screenHeight - 125);

        // Dessiner le texte en fonction de resultCatch
        if (resultCatch != -2){
            drawResultText(g);
        }
    }

    // Méthode pour dessiner le texte en fonction de resultCatch
    private void drawResultText(Graphics g) {
        System.out.println(resultCatch);
        g.setColor(Color.WHITE);

        String resultText = "";

        switch (resultCatch) {
            case -1:
                resultText = "Plus de place dans votre équipe, le Pokemon s'est échappé !";
                break;
            case 0:
                resultText = "Pokemon s'est échappé !";
                break;
            case 1:
                resultText = "Pokemon attrapé !";
                break;
            case 2:
                resultText = "Pokemon attrapé, vous avez récupérer un bonbon !";
                break;
            default:
                break;
        }

        int resultTextWidth = g.getFontMetrics().stringWidth(resultText);
        g.drawString(resultText, screenWidth / 2 - resultTextWidth / 2, screenHeight - 150);
    }

    // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
        // condition to change world
        handleCatch();
    }

    // method specific to HuntingWorld
    protected void handleCatch() {
        if (keyInputHandler.isNPressed() && !catchHandled) {
            System.out.println("N");
            changeToWorld(new HomeWorld(dresseur, "Pokemon by antocreadev"));
            stopBackgroundMusic();
        }
        if (keyInputHandler.isYPressed() && !catchHandled) {
            System.out.println("Y");
            resultCatch = dresseur.catchPokemon(wildPokemon);
            catchHandled = true; // Marquer la capture comme traitée
    
            // Utiliser un Thread pour introduire le délai
            Thread delayThread = new Thread(() -> {
                try {
                    // Mettre en pause l'exécution pendant 3 secondes
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    
                // Après le délai, changer de monde
                SwingUtilities.invokeLater(() -> {
                    changeToWorld(new HomeWorld(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                });
            });
    
            // Démarrer le Thread
            delayThread.start();
        }
    }
}
