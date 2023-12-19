import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HuntingWorld extends World {

    private Image wildPokemonImage;
    private Image attraperImage;
    private Image fuiteImage;
    private Image imageText;

    private Pokemon wildPokemon;
    private int resultCatch = -2;
    private boolean catchHandled = false;

    private Rectangle attraper_square;
    private Rectangle fuite_square;


    public HuntingWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        wildPokemon = Pokemon.getRandomPokemon();
        wildPokemonImage = new ImageIcon("assets/img/pokemons/front/" + wildPokemon.getId() + ".png").getImage();
        backgroundImage = new ImageIcon("assets/img/decors/wallHunterWorld.png").getImage();
        attraperImage = new ImageIcon("assets/img/decors/attraper.png").getImage();
        attraper_square = new Rectangle((screenWidth / 2) - 65, screenHeight - 145, 130, 65);
        fuite_square = new Rectangle((screenWidth / 2) - 35, screenHeight - 68, 70, 42);
        fuiteImage = new ImageIcon("assets/img/decors/fuite.png").getImage();
        imageText = new ImageIcon("assets/img/decors/imgText.png").getImage();
        loadBackgroundMusic("assets/sounds/fight.wav");

    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        g.drawImage(wildPokemonImage, screenWidth / 4, screenHeight / 8, screenWidth / 2, screenHeight / 2, this);

        g.setColor(new Color(0, 0, 0, 0));

        g.drawImage(attraperImage, (screenWidth / 2) - 65, screenHeight - 180, 130, 130, this);
        g.drawRect((screenWidth / 2) - 65, screenHeight - 145, 130, 65);

        g.drawImage(fuiteImage, (screenWidth / 2) - 35, screenHeight - 80, 70, 70, this);
        g.drawRect((screenWidth / 2) - 35, screenHeight - 68, 70, 42);

        // Dessiner le texte en fonction de resultCatch
        if (resultCatch != -2) {
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
                resultText = "Équipe complète, Pokemon échappé !";
                break;
            case 0:
                resultText = "Le Pokemon s'est échappé !";
                break;
            case 1:
                resultText = "Pokemon attrapé !";
                break;
            case 2:
                resultText = "Pokemon attrapé + un bonbon !";
                break;
            default:
                break;
        }

        // beautify
        g.setColor(Color.BLACK);
        // draw image

        g.drawImage(imageText, screenWidth / 2 -150, screenHeight - 420, 300, 150, this);
        // draw text 
        g.setColor(Color.BLACK);
        g.drawString(resultText, screenWidth / 2 + 20 - 150, screenHeight - 343);

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
        if (mouseInputHandler.isMouseClicked() && !catchHandled) {
            int mouseX = mouseInputHandler.getMouseX();
            int mouseY = mouseInputHandler.getMouseY();
            if (attraper_square.contains(mouseX, mouseY)) {
                System.out.println("Attraper");
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
            if (fuite_square.contains(mouseX, mouseY) && !catchHandled) {
                System.out.println("Fuite");
                changeToWorld(new HomeWorld(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
            }
        }
    }
}
