package cli;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Main {

  static String ASSETS_PATH = "./assets";
  static String POKEMONS_PATH = ASSETS_PATH + "/data/pokemons.csv";
  static String SAVES_PATH = "./saves_cli/";

  static String SERVER_IP = "localhost";

  // static Pokedex pokedex = new Pokedex();
  static Scanner reader = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println();

    Trainer trainer = null;

    // Choose a server
    System.out.println("Choose a server:");
    System.out.println("1. local");
    System.out.println("2. gelk.fr");
    int serverChoice = getIntInput();
    switch (serverChoice) {
      case 2:
        Main.SERVER_IP = "158.178.205.119";
        break;
      default:
        break;
    }
    System.out.println();

    // Check if there is a saved game
    File[] savedGame = Game.getSavedGame();
    if (savedGame != null) {
      System.out.println("Found " + savedGame.length + " saved game(s)");
      System.out.println();

      // list all the saved games
      System.out.println("0. Start a new game");
      for (int i = 0; i < savedGame.length; i++) {
        System.out.println(i + 1 + ". " + savedGame[i].getName());
      }
      int gameNumber = getIntInput();

      if (gameNumber == 0) {
        System.out.println();
        System.out.println("Starting a new game");
        System.out.println();
        trainer = createNewTrainer();
      } else {
        // Deserialize the saved game
        trainer = (Trainer) Serializer.deserialize(savedGame[gameNumber -
            1].getName());
        System.out.println("Game loaded !");
      }

    } else {
      System.out.println("No saved game found, starting a new game");
      System.out.println();
      trainer = createNewTrainer();
    }

    Game game = new Game(trainer);
    game.start();

    // // Choose game interface
    // System.out.println("Choose a game interface:");
    // System.out.println("1. CLI");
    // System.out.println("2. GUI");
    // int interfaceChoice = getIntInput();
    // switch (interfaceChoice) {
    // case 1:
    // Game game = new Game(trainer);
    // game.start();
    // break;
    // case 2:
    // Dresseur dresseur = new Dresseur(trainer.getName(), trainer.getPokemons());
    // GameGUI gameGUI = new GameGUI();
    // gameGUI.start(dresseur);
    // break;
    // default:
    // break;
    // }

    System.out.println();
  }

  /**
   * Create a new trainer and save it
   * 
   * @return The new trainer created
   */
  private static Trainer createNewTrainer() {
    // Ask the user for their name
    System.out.print("What is your name? ");
    String name = reader.nextLine();

    // Create a new trainer with the name
    Trainer trainer = new Trainer(name);
    System.out.println("New trainer created. Welcome " + trainer.getName() + "!");

    // Save the game
    trainer = Game.saveGame(trainer, false);

    return trainer;
  }

  /**
   * Get an integer input from the user
   * 
   * @return The integer input
   */
  public static int getIntInput() {
    System.out.print("-> ");
    try {
      int choice = reader.nextInt();
      reader.nextLine(); // Consume the \n character
      return choice;
    } catch (InputMismatchException e) {
      System.out.println("Invalid input. Please enter a valid integer.");
      reader.nextLine(); // Consume the invalid input
      return getIntInput(); // Recursive call to get a valid integer input
    }
  }

  /**
   * Display a message and wait for the user to press enter
   */
  public static void pressToContinue() {
    System.out.println();
    System.out.print("press enter to continue...");
    reader.nextLine();
  }

  /**
   * Play a music file
   * 
   * @param location
   */
  public static void PlayMusic(String location) {
    try {
      File musicPath = new File(location);

      if (musicPath.exists()) {
        AudioInputStream ais = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
      } else {
        System.out.println("Can't find music file");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Log a message
   * 
   * @param message
   */
  public static void log(Object o) {
    System.out.println("[LOG] > " + o);
  }

  public static void log(Object o, String prefix) {
    System.out.println("[" + prefix + "] > " + o);
  }
}
