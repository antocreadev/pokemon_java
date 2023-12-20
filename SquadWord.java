import javax.swing.*;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class SquadWord extends World {
    private Rectangle btnBack;
    private ArrayList<Rectangle> btnRemovePokemon;
    private ArrayList<Rectangle> btnEvolutionPokemon;
    private ArrayList<Rectangle> btnUpgradePokemon;

    private Image imageBtnRemovePokemon;
    private Image imageBtnBack;
    private Image imageIconEvolutionPokemon;
    private Image imageIconUpgradePokemon;

    public SquadWord(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        backgroundImage = new ImageIcon("assets/img/decors/bgBlack.png").getImage();
        loadBackgroundMusic("assets/sounds/menu.wav");
        btnBack = new Rectangle(370, 370, 30, 30);
        btnRemovePokemon = new ArrayList<Rectangle>();
        btnEvolutionPokemon = new ArrayList<Rectangle>();
        btnUpgradePokemon = new ArrayList<Rectangle>();
        imageBtnRemovePokemon = new ImageIcon("assets/img/decors/iconRemovePokemon.png").getImage();
        imageBtnBack = new ImageIcon("assets/img/decors/iconQuitPokemon.png").getImage();
        imageIconEvolutionPokemon = new ImageIcon("assets/img/decors/iconEvolutionPokemon.png").getImage();
        imageIconUpgradePokemon = new ImageIcon("assets/img/decors/iconUpgradePokemon.png").getImage();

    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);

        // Draw "Liste des Pokemons" in bold at the top
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String title = "Ton équipe de pokemons";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, screenWidth / 2 - titleWidth / 2, 50);

        // Pokemons of player with for loop
        for (int i = 0; i < dresseur.getPokemons().size(); i++) {
            int pokemonX = (i % 2 == 0) ? 0 : screenWidth / 2;
            // int pokemonY = i*screenHeight / 6 +30;
            int pokemonY = (i % 2 == 0) ? i * screenHeight / 6 + 60 : (i - 1) * screenHeight / 6 + 60;

            // Draw Pokemon sprite
            g.drawImage(new ImageIcon("assets/img/pokemons/front/" + dresseur.getPokemons().get(i).getId() + ".png")
                    .getImage(), pokemonX, pokemonY, screenWidth / 8, screenHeight / 8, this);

            // Draw Pokemon information rectangle
            drawPokemonInfo(g, dresseur.getPokemons().get(i), pokemonX, pokemonY);

        }
        // draw red rect bottom
        g.setColor(new Color(255, 0, 0, 0));
        g.drawRect(btnBack.x, btnBack.y, btnBack.width, btnBack.height);
        g.drawImage(imageBtnBack, btnBack.x, btnBack.y, btnBack.width, btnBack.height, this);
    }

    // Method to draw Pokemon information rectangle
    private void drawPokemonInfo(Graphics g, Pokemon pokemon, int x, int y) {
        // Set the color and font for the information text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Draw Pokemon name, attack, and HP inside the rectangle
        g.setColor(Color.WHITE);
        g.drawString(pokemon.getName(), x + screenWidth / 8 + 10, y + 20);
        g.drawString("\u2694 : " + pokemon.getAttack() + " / \u2764 : " + pokemon.getHp(), x + screenWidth / 8 + 10,
                y + 40);

        // draw btn remove pokemon
        g.setColor(new Color(255, 0, 0, 0));
        g.drawRect(x + screenWidth / 8, y + 47, 20, 20);
        // add rect in arraylist
        btnRemovePokemon.add(new Rectangle(x + screenWidth / 8, y + 47, 20, 20));
        g.drawImage(imageBtnRemovePokemon, x + screenWidth / 8, y + 47, 20, 20, this);

        // draw icon upgrade pokemon
        if (dresseur.browseRareCandies(pokemon.getType()) >= 1) {
            btnUpgradePokemon.add(new Rectangle(x + screenWidth / 8 + 30, y + 47, 20, 20));
            g.drawImage(imageIconUpgradePokemon, x + screenWidth / 8 + 30, y + 47, 20, 20, this);
        } else {
            // add fictive rect for not click
            btnUpgradePokemon.add(new Rectangle(0, 0, 0, 0));
        }

        // draw icon evolution pokemon
        if (pokemon.getEvolution().size() > 0 && dresseur.browseRareCandies(pokemon.getType()) >= 5) {
            btnEvolutionPokemon.add(new Rectangle(x + screenWidth / 8 + 60, y + 47, 20, 20));
            g.drawImage(imageIconEvolutionPokemon, x + screenWidth / 8 + 60, y + 47, 20, 20, this);
        } else {
            // add fictive rect for not click
            btnEvolutionPokemon.add(new Rectangle(0, 0, 0, 0));
        }

        // draw rect
        g.setColor(Color.WHITE);
        g.drawRect(x + screenWidth / 8, y, screenWidth / 4, screenHeight / 6);
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

            for (int i = 0; i < btnRemovePokemon.size(); i++) {
                if (btnRemovePokemon.get(i).contains(mouseX, mouseY)) {
                    // System.out.println("Remove pokemon");
                    dresseur.getPokemons().remove(i);
                    // refresh world
                    changeToWorld(new SquadWord(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                    return;
                }
            }

            for (int i = 0; i < btnUpgradePokemon.size(); i++) {
                if (btnUpgradePokemon.get(i).contains(mouseX, mouseY)) {
                    dresseur.getPokemons().get(i).eatCandy(1);
                    dresseur.removeRareCandy(dresseur.getPokemons().get(i).getType());
                    // refresh world
                    changeToWorld(new SquadWord(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                    return;
                }
            }

            for (int i = 0; i < btnEvolutionPokemon.size(); i++) {
                if (btnEvolutionPokemon.get(i).contains(mouseX, mouseY)) {
                    System.out.println(Pokedex.getPokedex());
                    System.out.println(btnEvolutionPokemon.get(i));
                    System.out.println("Evolution pokemon " + i + " " + dresseur.getPokemons().get(i).getName());
                    dresseur.getPokemons().get(i).eatCandy(5);
                    dresseur.getPokemons().get(i).evolve();
                    changeToWorld(new SquadWord(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                    return;
                }
            }
        }
    }
}
