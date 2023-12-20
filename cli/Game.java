package cli;

import java.io.File;

public class Game {
  public static int ATTACK_COEFFICIENT = 10;

  private Trainer trainer;
  private Client client;

  public Game(Trainer trainer) {
    this.trainer = trainer;
  }

  /**
   * Start the game main loop
   */
  public void start() {
    System.out.println();
    System.out.println("--------- Welcome to Pokemed! ---------");
    System.out.println("This is a game where you can catch and evolve Pokemon");
    System.out.println("You can also battle with other trainers");
    System.out.println();

    Main.PlayMusic(Main.ASSETS_PATH + "/JubilifeCityNight8bit.wav");

    // Main loop
    while (true) {
      // Display the main menu
      displayMainMenu();

      // Get the user's choice
      int choice = Main.getIntInput();
      System.out.println();

      switch (choice) {
        case 1:
          catchAPokemon();
          break;
        case 2:
          listOwnedPokemons();
          break;
        case 3:
          startOnlineBattle();
          break;
        case 4:
          listBackpackItems();
          break;
        case 8:
          listPokedex();
          break;
        case 9:
          saveGame(trainer, false);
          break;
        case 69:
          // GET 99 RARE CANDIES
          for (int i = 0; i < 99; i++) {
            trainer.getRareCandies().add(new RareCandy(PokemonType.NORMAL));
          }
          trainer.addPokemon(Pokedex.getPokemonByName("Arceus"));
          trainer.addPokemon(Pokedex.getPokemonByName("Rattata"));
          break;
        case 0:
        default:
          try {
            client.disconnect();
          } catch (Exception e) {
          }
          System.out.println("Exiting ...");
          System.exit(0);
          break;
      }

      System.out.println();
    }

  }

  /**
   * Display the main menu
   */
  private void displayMainMenu() {
    System.out.println();
    System.out.println(String.format("--- %s's Profile ---", this.trainer.getName()));
    System.out.println();
    System.out.println("1. Catch a pokemon");
    System.out.println("2. List owned pokemons");
    System.out.println("3. Start a battle");
    System.out.println("4. List backpack's items");
    System.out.println();
    System.out.println("8. List all pokemons in the pokedex");
    System.out.println("9. Save the game");
    System.out.println("0. Exit");
  }

  /**
   * Catch a wild pokemon
   */
  private void catchAPokemon() {
    System.out.println("Entering the wild ...");

    try {
      Thread.sleep(400);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Pokemon wildPokemon = Pokemon.getRandomPokemon();

    System.out.println("A wild " + wildPokemon.getName() + " appeared !");
    System.out.println();
    System.out.println("1. Catch");
    System.out.println("2. Run");
    int choice = Main.getIntInput();
    switch (choice) {
      case 1:
        trainer.catchPokemon(wildPokemon);
        Main.pressToContinue();
        break;
      case 2:
      default:
        System.out.println("You ran away");
        break;
    }
  }

  /**
   * List all owned pokemons
   */
  private void listOwnedPokemons() {
    trainer.listOwnedPokemons();
    System.out.println();
    System.out.println("0. Back");
    int backChoice = Main.getIntInput();
    if (backChoice != 0 && backChoice <= trainer.getPokemons().size()) {
      Pokemon selectedPokemon = trainer.getPokemons().get(backChoice - 1);
      System.out.println();
      selectedPokemon.editPokemon(trainer.getRareCandies());
    }
  }

  /**
   * Start an online battle
   */
  private void startOnlineBattle() {
    System.out.println("Starting an online battle.");
    System.out.println("Connecting to the server ...");

    // Make sure trainer has at least 1 pokemon
    if (trainer.getPokemons().size() == 0) {
      System.out.println("You need at least 1 pokemon to start a battle");
      Main.pressToContinue();
      return;
    }

    try {
      Thread.sleep(500);
      client = new Client(trainer);
      client.run();
    } catch (Exception e) {
      System.out.println("[EXCEPTION] " + e.getMessage());
      Main.pressToContinue();
      return;
    }
  }

  /**
   * List all items in the backpack
   */
  private void listBackpackItems() {
    System.out.println("--------- Backpack ---------");
    System.out.println();
    for (PokemonType type : PokemonType.values()) {
      int count = 0;
      for (RareCandy rareCandy : trainer.getRareCandies()) {
        if (rareCandy.getType() == type) {
          count++;
        }
      }
      if (count > 0) {
        System.out.println(String.format("%s %s: %d", type.getEmojiType(), type.toString(), count));
      }
    }
    Main.pressToContinue();
  }

  /**
   * List all pokemons in the pokedex
   */
  private void listPokedex() {
    System.out.println("--------- Pokedex ---------");
    System.out.println();
    for (Pokemon pokemon : Pokedex.getPokedex()) {
      System.out.println(pokemon);
    }
    Main.pressToContinue();

  }

  // ------------------
  // ----- STATIC -----
  // ------------------

  /**
   * Save the game to a file in the saves folder
   * 
   * @param trainer
   * @param force   Force the save even if a saved game with the same name already
   */
  public static Trainer saveGame(Trainer trainer, boolean force) {
    System.out.println();
    boolean canSave = force;

    if (!force) {
      if (!Serializer.fileExists(trainer.getName())) {
        canSave = true;
      } else {
        System.out.println("A saved game with the same name already exists.");
        System.out.print("Do you want to overwrite the saved game? (y/n) ");
        String answer;
        do {
          answer = Main.reader.nextLine();
          if (answer.toLowerCase().equals("y")) {
            canSave = true;
            break;
          } else {
            System.out.println("Saving game aborted");
            return (Trainer) Serializer.deserialize(trainer.getName());
          }
        } while (answer != "y" || answer != "n");
      }
    }

    // If no saved game with the same name exists, or if the user wants to overwrite
    if (canSave) {
      System.out.println("Saving game...");
      try {
        Thread.sleep(900);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      Serializer.serialize(trainer.getName(), trainer);

      System.out.println("Game saved !");
      System.out.println();
      return trainer;
    } else {
      return trainer;
    }
  }

  /**
   * Get all saved games
   * 
   * @return List of saved games
   */
  public static File[] getSavedGame() {
    try {
      File folder = new File(Main.SAVES_PATH);

      // If the saves folder doesn't exist, create it
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
