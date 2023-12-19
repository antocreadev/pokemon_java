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