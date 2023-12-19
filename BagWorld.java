import javax.swing.*;
import java.awt.*;

public class BagWorld extends World {
    private Rectangle btnBack;
    private Image imageBtnBack;
    public BagWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        // System.out.println(Pokedex.getPokedex());
        backgroundImage = new ImageIcon("assets/img/decors/bgBlack.png").getImage();
        loadBackgroundMusic("assets/sounds/menu.wav");
        btnBack = new Rectangle(370, 370, 30, 30);
        imageBtnBack = new ImageIcon("assets/img/decors/iconQuitPokemon.png").getImage();

    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        // draw btn back

        // for each dresseur.getRareCandiesType()
        System.out.println(dresseur.getRareCandiesType());



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
                System.out.println("Back");
                // Change world
                changeToWorld(new HomeWorld(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
            }
        }

    }


}
