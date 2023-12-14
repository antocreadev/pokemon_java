import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HuntingWorld extends World {

    private Image wildPokemonImage;
    private Pokemon wildPokemon;

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
        g.drawImage(wildPokemonImage, screenWidth/4, screenHeight/8, screenWidth/2, screenHeight/2, this);




        g.setColor(Color.WHITE);
        String label = "(y) Attraper le Pokemon ?";
        int labelWidth = g.getFontMetrics().stringWidth(label);
        g.drawString(label, screenWidth/2 - labelWidth/2, screenHeight-100);


        g.setColor(Color.WHITE);
        String label2 = "(n) Fuite ?";
        int label2Width = g.getFontMetrics().stringWidth(label);
        g.drawString(label2, screenWidth/2 - label2Width/2, screenHeight-125);
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
        if (keyInputHandler.isNPressed()) {
            System.out.println("N");
            changeToWorld(new HomeWorld(dresseur, playerName));
            stopBackgroundMusic();
        }
        if (keyInputHandler.isYPressed()) {
            System.out.println("Y");
            dresseur.addPokemon(wildPokemon);
            changeToWorld(new HomeWorld(dresseur, playerName));
            stopBackgroundMusic();
        }

    }

}
