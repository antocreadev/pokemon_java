import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {
        public static void main(String[] args) {
        Dresseur giovanni = new Dresseur("Giovanni", new ArrayList<Pokemon>());
        SwingUtilities.invokeLater(() -> {
            GameInterface game = new GameInterface(giovanni);
            game.setVisible(true);
        });
    }
}
