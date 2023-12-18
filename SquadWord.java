import javax.swing.*;
import java.awt.*;

public class SquadWord extends World {

    public SquadWord(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    protected void init() {
        backgroundImage = new ImageIcon("assets/img/decors/bgBlack.png").getImage();
        loadBackgroundMusic("assets/sounds/menu.wav");
    }

    protected void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);

        // Pokemons of player with for loop
        for (int i = 0; i < dresseur.getPokemons().size(); i++) {
            int pokemonX = (i % 2 == 0) ? 0 : screenWidth / 2;
            // int pokemonY = i*screenHeight / 6 +30;
            int pokemonY = (i%2 == 0) ? i*screenHeight / 6 +40 : (i-1)*screenHeight / 6 +40;

            // Draw Pokemon sprite
            g.drawImage(new ImageIcon("assets/img/pokemons/front/" + dresseur.getPokemons().get(i).getId() + ".png")
                    .getImage(), pokemonX, pokemonY, screenWidth / 8, screenHeight / 8, this);

            // Draw Pokemon information rectangle
            drawPokemonInfo(g, dresseur.getPokemons().get(i), pokemonX, pokemonY);
        }
    }

    // Method to draw Pokemon information rectangle
    private void drawPokemonInfo(Graphics g, Pokemon pokemon, int x, int y) {
        // Set the color and font for the information text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Draw the rectangle
        // g.fillRect(x + screenWidth / 8, y, screenWidth / 4, screenHeight / 6);

        // Draw Pokemon name, attack, and HP inside the rectangle
        g.setColor(Color.WHITE);
        g.drawString(pokemon.getName(), x + screenWidth / 8 + 10, y + 20);
        g.drawString("\u2694 : " + pokemon.getAttack() +" / \u2764 : "+pokemon.getHp(), x + screenWidth / 8 + 10, y + 40 );
    }

    // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
    }
}
