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

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        g.drawImage(wildPokemonImage, screenWidth/4, screenHeight/8, screenWidth/2, screenHeight/2, this);

        g.setColor(Color.RED);
        g.fillRect(screenWidth/8, screenHeight/8, screenWidth/4, screenHeight/4);
    }

}
