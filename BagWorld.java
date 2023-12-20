import javax.swing.*;

import cli.PokemonType;

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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String title = "Liste des tes bonbons";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, screenWidth / 2 - titleWidth / 2, 50);

        // for each dresseur.getRareCandiesType()
        for (int i = 0; i < dresseur.getRareCandiesType().size(); i++) {
            PokemonType pokemonType = dresseur.getRareCandiesType().get(i);
            // System.out.println(pokemonType + " " + dresseur.browseRareCandies(pokemonType));
            // draw text like list of rare candies
            int x = screenWidth / 2 - 200;
            // int pokemonY = i*screenHeight / 6 +30;
            int y = i * screenHeight / 17 + 60;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            String text = pokemonType + " : " + dresseur.browseRareCandies(pokemonType);
            g.drawString(text, x, y);
            





            
        }
        



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
