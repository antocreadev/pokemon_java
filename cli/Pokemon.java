package cli;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Pokemon
 * 
 * Represents a Pokemon
 */
public class Pokemon implements Serializable {
  private int id;
  private String name;
  private int hp;
  private int attack;
  private PokemonType type;
  private int evolutionStage;
  private ArrayList<Pokemon> evolution = new ArrayList<>();
  private String evolutionName;

  /**
   * Constructor
   * 
   * @param id             The Pokemon's id
   * @param name           The Pokemon's name
   * @param hp             The Pokemon's HP
   * @param attack         The Pokemon's attack
   * @param type           The Pokemon's type
   * @param evolutionStage The Pokemon's evolution stage
   * @param evolution      The Pokemon's evolution
   * @param evolutionName  The Pokemon's evolution name
   */
  public Pokemon(int id, String name, int hp, int attack, PokemonType type, int evolutionStage,
      ArrayList<Pokemon> evolution, String evolutionName) {
    setId(id);
    setName(name);
    setHp(hp);
    setAttack(attack);
    setType(type);
    setEvolutionStage(evolutionStage);
    if (evolution != null)
      setEvolution(evolution);
    setEvolutionName(evolutionName);
  }

  public Pokemon(Pokemon pokemon) {
    this(pokemon.getId(), pokemon.getName(), pokemon.getHp(), pokemon.getAttack(), pokemon.getType(),
        pokemon.getEvolutionStage(), pokemon.getEvolution(), pokemon.getEvolutionName());
  }

  @Override
  public String toString() {
    return String.format("%s %s (HP: %d, Attack: %d) %s", this.type.getEmojiType(), getName(), getHp(),
        getAttack(), getEvolution());
  }

  /**
   * Eat a certain amount of candy to increase HP and attack
   * Can evolve if 5 candies are eaten
   * 
   * @param amount The amount of candy to eat
   */
  public void eatCandy(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }

    // Check if the trainer have enough rare candies
    if (amount >= 5) {
      String answser;
      System.out.println("You have more than 5 candies, you can evolve your pokemon.");
      System.out.print("Do you want to evolve your pokemon ? (y/n) ");
      do {
        answser = Main.reader.nextLine();
        // If yes, evolve the pokemon, remove 5 candies
        if (answser.equals("y")) {
          try {
            evolve();
            amount -= 5;
            break;
          } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
            return;
          }
        } else {
          break;
        }
      } while (answser != "y" || answser != "n");
    }

    if (amount > 0) {
      double increaseFactor = amount * 0.1; // Adjust the factor as needed
      int hpIncrease = (int) (getHp() * increaseFactor);
      int attackIncrease = (int) (getAttack() * increaseFactor);

      setHp(getHp() + hpIncrease);
      setAttack(getAttack() + attackIncrease);

      System.out.println("Eating candy ...");
      System.out.println(String.format("HP: %d (+%d)", getHp(), hpIncrease));
      System.out.println(String.format("Attack: %d (+%d)", getAttack(), attackIncrease));
    }

    Main.pressToContinue();
  }

  public void eatCandyGUI(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }
    if (amount > 0) {
      double increaseFactor = amount * 0.1; // Adjust the factor as needed
      int hpIncrease = (int) (getHp() * increaseFactor);
      int attackIncrease = (int) (getAttack() * increaseFactor);

      setHp(getHp() + hpIncrease);
      setAttack(getAttack() + attackIncrease);

      System.out.println("Eating candy ...");
      System.out.println(String.format("HP: %d (+%d)", getHp(), hpIncrease));
      System.out.println(String.format("Attack: %d (+%d)", getAttack(), attackIncrease));
    }

    System.out.println();
  }

  /**
   * Evolve the Pokemon
   * 
   * @throws UnsupportedOperationException If the Pokemon cannot evolve
   */
  public void evolve() {
    String oldPokemonName = getName();
    System.out.println(oldPokemonName + " is evolving ...");

    // Copy the evolution to the current pokemon
    Pokemon evolution = getEvolution().get(0);
    if (evolution != null) {
      setId(evolution.getId());
      setName(evolution.getName());
      setHp(evolution.getHp());
      setAttack(evolution.getAttack());
      setType(evolution.getType());
      setEvolutionStage(evolution.getEvolutionStage());
      setEvolution(evolution.getEvolution());
    }

    System.out.println("Congratulations !");
    System.out.println(oldPokemonName + " evolved into " + getName() + " !");
  }

  /**
   * Get a random Pokemon from the Pokedex
   * 
   * @return A random first stage Pokemon
   */
  public static Pokemon getRandomPokemon() {
    Pokemon wildPokemon = null;
    do {
      int random = (int) (Math.random() * Pokedex.getPokedex().size());
      wildPokemon = Pokedex.getPokedex().get(random);
    } while (wildPokemon.getEvolutionStage() > 1);

    Pokemon newPokemon = new Pokemon(wildPokemon.getId(), wildPokemon.getName(), wildPokemon.getHp(),
        wildPokemon.getAttack(), wildPokemon.getType(), wildPokemon.getEvolutionStage(), wildPokemon.getEvolution(),
        wildPokemon.getEvolutionName());
    return newPokemon;
  }

  /**
   * Edit the Pokemon
   */
  public void editPokemon(ArrayList<RareCandy> rareCandies) {
    System.out.println("Editing " + this);
    System.out.println("1. Edit name");
    System.out.println("2. Eat candy");
    System.out.println("0. Back");
    int choice = Main.getIntInput();
    switch (choice) {
      case 1:
        System.out.print("New name: ");
        String newName = Main.reader.nextLine();
        setName(newName);
        System.out.println("Name changed !");
        break;
      case 2:
        // Get the amount of candies that have the same type as the pokemon
        ArrayList<RareCandy> sameTypeCandies = new ArrayList<>();
        for (RareCandy candy : rareCandies) {
          if (candy.getType() == getType()) {
            sameTypeCandies.add(candy);
          }
        }
        if (sameTypeCandies.size() == 0) {
          System.out.println("You don't have any candies for this pokemon");
          break;
        }
        System.out.println("You have " + sameTypeCandies.size() + " rare candies");
        System.out.print("How many candies do you want to eat ? ");
        int amount = Main.getIntInput();
        try {
          eatCandy(amount);
          for (int i = 0; i < amount; i++) {
            rareCandies.remove(sameTypeCandies.get(i));
          }
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
        break;
      case 0:
      default:
        break;
    }
  }

  // Method to add a Pokemon to the evolution list
  public void addEvolution(Pokemon evolvedForm) {
    this.evolution.add(evolvedForm);
  }

  // ------------------
  // Getters and setters
  // ------------------

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name.length() < 2) {
      throw new IllegalArgumentException("Name must be at least 2 characters long");
    }
    this.name = name;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
    if (this.hp < 0) {
      // Pokemon fainted
      this.hp = 0;
    }
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    if (attack < 0) {
      throw new IllegalArgumentException("Attack must be greater than 0");
    }
    this.attack = attack;
  }

  public PokemonType getType() {
    return type;
  }

  public void setType(PokemonType type) {
    this.type = type;
  }

  public int getEvolutionStage() {
    return evolutionStage;
  }

  public void setEvolutionStage(int evolutionStage) {
    this.evolutionStage = evolutionStage;
  }

  public ArrayList<Pokemon> getEvolution() {
    return evolution;
  }

  public void setEvolution(ArrayList<Pokemon> evolution) {
    this.evolution = evolution;
  }

  public String getEvolutionName() {
    return evolutionName;
  }

  public void setEvolutionName(String evolutionName) {
    this.evolutionName = evolutionName;
  }

}