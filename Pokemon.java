import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
  private int candy;

  /**
   * Constructor
   * 
   * @param name           The Pokemon's name
   * @param hp             The Pokemon's HP
   * @param attack         The Pokemon's attack
   * @param type           The Pokemon's type
   * @param evolutionStage The Pokemon's evolution stage
   * @param evolution      The Pokemon's evolution
   * @param candy          The Pokemon's candy
   */
  public Pokemon(int id, String name, int hp, int attack, PokemonType type, int evolutionStage, ArrayList<Pokemon> evolution) {
    setId(id);
    setName(name);
    setHp(hp);
    setAttack(attack);
    setType(type);
    setEvolutionStage(evolutionStage);
    setEvolution(evolution);
    setCandy(0);
  }

  @Override
  public String toString() {
    return String.format("%s %s (HP: %d, Attack: %d), %s", this.type.getEmojiType(), getName(), getHp(),
        getAttack(), getEvolution());
  }

//   /**
//    * Eat a certain amount of candy to increase HP and attack
//    * Can evolve if 5 candies are eaten
//    * 
//    * @param amount The amount of candy to eat
//    */
//   public void eat(int amount) {
//     if (amount < 0) {
//       throw new IllegalArgumentException("Amount must be greater than 0");
//     }

//     // Check if the trainer have enough rare candies

//     if (amount > 5) {
//       String answser;
//       do {
//         System.out.println("You have more than 5 candies, you can evolve your pokemon.");
//         System.out.print("Do you want to evolve your pokemon ? (y/n) ");
//         answser = Main.reader.nextLine();
//       } while (answser != "y" || answser != "n");
//       return;
//     }

//     double increaseFactor = amount * 0.1; // Adjust the factor as needed
//     int hpIncrease = (int) (getHp() * increaseFactor);
//     int attackIncrease = (int) (getAttack() * increaseFactor);

//     setHp(getHp() + hpIncrease);
//     setAttack(getAttack() + attackIncrease);

//     System.out.println("Eating candy ...");
//     System.out.println(String.format("HP: %d (+%d)", getHp(), hpIncrease));
//     System.out.println(String.format("Attack: %d (+%d)", getAttack(), attackIncrease));
//     System.out.println();
//   }

  /**
   * Evolve the Pokemon
   * 
   * @throws UnsupportedOperationException If the Pokemon cannot evolve
   */
  public void evolve() {
    if (this.candy < 5) {
      System.out.println("You don't have enough candies to evolve your pokemon.");
    }
  }

  /**
   * Get a random Pokemon from the Pokedex
   * 
   * @return A random first stage Pokemon
   */
    public static Pokemon getRandomPokemon() {
        ArrayList<Pokemon> pokedex = Pokedex.getPokedex();
        ArrayList<Pokemon> firstStagePokemon = new ArrayList<>();
        for (Pokemon pokemon : pokedex) {
            if (pokemon.getEvolutionStage() == 1) {
                firstStagePokemon.add(pokemon);
            }
        }
        int randomIndex = new Random().nextInt(firstStagePokemon.size());
        Pokemon wildPokemon = firstStagePokemon.get(randomIndex);
        return wildPokemon;
    }

  /**
   * Edit the Pokemon
   */
//   public void editPokemon() {
//     System.out.println("Editing " + this);
//     System.out.println("1. Edit name");
//     // 2. eat candy
//     System.out.println("0. Back");
//     System.out.print("-> ");
//     int choice = Integer.parseInt(Main.reader.nextLine());
//     switch (choice) {
//       case 1:
//         System.out.print("New name: ");
//         String newName = Main.reader.nextLine();
//         setName(newName);
//         System.out.println("Name changed !");
//         break;

//       case 0:
//       default:
//         break;
//     }
//   }

    /**
     * Increment the candy
     */
  public void incrementeCandy() {
        this.candy += 1;
    }

  // ------------------
  // Getters and setters
  // ------------------

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
    if (hp < 0) {
      throw new IllegalArgumentException("HP must be greater than 0");
    }
    this.hp = hp;
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

  public int getCandy() {
        return candy;
    }

  public void setCandy(int candy) {
        this.candy = candy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}