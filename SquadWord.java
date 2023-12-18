import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SquadWord extends World {
    private Rectangle btnBack;
private ArrayList<Rectangle> btnRemovePokemon;

    public SquadWord(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        backgroundImage = new ImageIcon("assets/img/decors/bgBlack.png").getImage();
        loadBackgroundMusic("assets/sounds/menu.wav");
        btnBack = new Rectangle(370, 370, 20, 20);
        btnRemovePokemon = new ArrayList<Rectangle>();
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
        g.setColor(Color.RED);
        g.drawRect(btnBack.x, btnBack.y, btnBack.width, btnBack.height);
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
        g.setColor(Color.orange);
        g.drawRect(x + screenWidth / 8, y + 47, 20, 20);
        // add rect in arraylist
        btnRemovePokemon.add(new Rectangle(x + screenWidth / 8, y + 47, 20, 20));

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
                System.out.println("Back");
                // Change world
                changeToWorld(new HomeWorld(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
            }

            for (int i = 0; i < btnRemovePokemon.size(); i++) {
                if (btnRemovePokemon.get(i).contains(mouseX, mouseY)) {
                    System.out.println("Remove pokemon");
                    dresseur.getPokemons().remove(i);
                    // refresh world
                    changeToWorld(new SquadWord(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                }
            }
        }
    }
}
