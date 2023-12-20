import java.io.File;

import javax.swing.SwingUtilities;

public class Main {
  static String ASSETS_PATH = "./assets";
  static String POKEMONS_PATH = ASSETS_PATH + "/data/pokemons.csv";
  static String SAVES_PATH = "./saves/";
  static String SERVER_IP = "localhost";

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new Pokedex();
      new MenuWorld(null, "Pokemon by antocreadev");
    });
  }

  /**
   * Save the game to a file in the saves folder
   * 
   * @param dresseur
   * @param force    Force the save even if a saved game with the same name
   *                 already
   */
  public static Dresseur saveGame(Dresseur dresseur) {
    System.out.println();

    Serializer.serialize(dresseur.getName(), dresseur);

    System.out.println("Game saved !");
    System.out.println();
    return dresseur;
  }

  /**
   * Get all saved games
   * 
   * @return List of saved games
   */
  public static File[] getSavedGame() {
    try {
      File folder = new File(Main.SAVES_PATH);

      if (!folder.exists()) {
        folder.mkdir();
      }

      File[] listOfFiles = folder.listFiles();

      if (listOfFiles.length == 0) {
        return null;
      } else {
        return listOfFiles;
      }
    } catch (NullPointerException npe) {
      System.err.println("[ERROR] " + npe.getMessage());
      System.exit(1);
      return null;
    }
  }

}
