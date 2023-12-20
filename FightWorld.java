import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FightWorld extends World {
    private Rectangle btnBack;
    private Image imageBtnBack;


    public FightWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
        imageBtnBack = new ImageIcon("assets/img/decors/iconQuitPokemon.png").getImage();
        btnBack = new Rectangle(370, 370, 30, 30);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        backgroundImage = new ImageIcon("assets/img/decors/bgFight.png").getImage();
        loadBackgroundMusic("assets/sounds/arena.wav");
    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        // draw btn back
        g.drawImage(imageBtnBack, btnBack.x, btnBack.y, btnBack.width, btnBack.height, this);
        g.setColor(new Color(255, 0, 0, 0));
        g.drawRect(btnBack.x, btnBack.y, btnBack.width, btnBack.height);

    }

   // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
         // Utilisez les informations de la souris pour détecter les clics
         if (mouseInputHandler.isMouseClicked()) {
            int mouseX = mouseInputHandler.getMouseX();
            int mouseY = mouseInputHandler.getMouseY();
            if (btnBack.contains(mouseX, mouseY)) {
                // System.out.println("Back");
                // Change world
                changeToWorld(new HomeWorld(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
                return;
            }
        }
    }


}
