import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Pokedex();
         

            Dresseur giovanni = new Dresseur("Giovanni", new ArrayList<Pokemon>());
            HomeWorld game = new HomeWorld(giovanni, "Pokemon by antocreadev");
            game.setVisible(true);
        });
    }
}
