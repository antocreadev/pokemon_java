import javax.swing.*;
import java.awt.*;

public class BagWorld extends World {

    public BagWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        backgroundImage = new ImageIcon("assets/img/decors/bgBlack.png").getImage();
        loadBackgroundMusic("assets/sounds/menu.wav");
    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
    }

   // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
    }


}
