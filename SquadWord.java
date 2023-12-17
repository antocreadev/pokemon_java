import javax.swing.*;
import java.awt.*;

public class SquadWord extends World {

    public SquadWord(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        backgroundImage = new ImageIcon("assets/img/decors/bgBlack.png").getImage();
        loadBackgroundMusic("assets/sounds/menu.wav");
    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        // pokemons of player with for i
        for (int i = 0; i < dresseur.getPokemons().size(); i++) {
            g.drawImage(new ImageIcon("assets/img/pokemons/front/" + dresseur.getPokemons().get(i).getId() + ".png")
                    .getImage(), i * 25, 20, screenWidth / 8, screenHeight / 8, this);
        }
    }

   // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
    }


}
