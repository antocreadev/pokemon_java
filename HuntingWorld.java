import javax.swing.*;
import java.awt.*;

public class HuntingWorld extends World {

    private Image wildPokemonImage;

    public HuntingWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        Pokemon wildPokemon = Pokemon.getRandomPokemon();
        wildPokemonImage = new ImageIcon("assets/img/pokemons/front/" + wildPokemon.getId() + ".png").getImage();
        backgroundImage = new ImageIcon("assets/img/decors/wallHunterWorld.png").getImage();
        loadBackgroundMusic("assets/sounds/fight.wav");
    }

    protected void gameLoop() {

        Graphics g = bufferStrategy.getDrawGraphics();

        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        
        g.drawImage(wildPokemonImage, 0, 0, screenWidth, screenHeight, this);
        
        draw(g);

        bufferStrategy.show();
        g.dispose();
    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        g.drawImage(wildPokemonImage, 0, 0, screenWidth, screenHeight, this);
    }

}
