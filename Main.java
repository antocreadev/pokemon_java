import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {
        public static void main(String[] args) {
        Dresseur giovanni = new Dresseur("Giovanni", new ArrayList<Pokemon>());
        SwingUtilities.invokeLater(() -> {
            HomeWorld game = new HomeWorld(giovanni);
            game.setVisible(true);
        });
    }
}