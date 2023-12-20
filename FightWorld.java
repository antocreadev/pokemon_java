import javax.swing.*;

import cli.Client;
import cli.Trainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class FightWorld extends World {
    private Rectangle btnBack;
    private Image imageBtnBack;

    private Image imageAttaque;
    private Rectangle attaque_square;

    public FightWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
        imageBtnBack = new ImageIcon("assets/img/decors/iconQuitPokemon.png").getImage();
        btnBack = new Rectangle(370, 370, 30, 30);
        imageAttaque = new ImageIcon("assets/img/decors/lancerCombat.png").getImage();
        attaque_square = new Rectangle((screenWidth / 2) - 65, screenHeight - 145, 130, 65);
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

        // draw btn attaque
        g.drawImage(imageAttaque, (screenWidth / 2) - 65, screenHeight - 180, 130, 130, this);
        g.drawRect((screenWidth / 2) - 65, screenHeight - 145, 130, 65);

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

            if (attaque_square.contains(mouseX, mouseY)) {
                // System.out.println("Attaque");
                // Change world
                Trainer trainer = new Trainer(dresseur.getName());
                trainer.setPokemons(dresseur.getPokemons());
                Client client;
                try {
                    client = new Client(trainer);
                    client.run();
                } catch (Exception e) {
                    // TODO: handle exception
                    System.err
                            .println("[ERROR - FightWorld.java] : " + e.getClass().getName() + " --" + e.getMessage());
                    dresseur.setPokemons(trainer.getPokemons());
                }
            }
        }
    }

}
