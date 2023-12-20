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
  private String evolutionName;

  /**
   * Constructor
   * 
   * @param name           The Pokemon's name
   * @param hp             The Pokemon's HP
   * @param attack         The Pokemon's attack
   * @param type           The Pokemon's type
   * @param evolutionStage The Pokemon's evolution stage
   * @param evolution      The Pokemon's evolution
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
    return String.format("%s %s (HP: %d, Attack: %d), %s", this.type.getEmojiType(), getName(), getHp(),
        getAttack(), getEvolution());
  }

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

  public void eatCandy(int amount) {
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

  public void addEvolution(Pokemon evolution) {
    this.evolution.add(evolution);
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEvolutionName() {
    return evolutionName;
  }

  public void setEvolutionName(String evolutionName) {
    this.evolutionName = evolutionName;
  }
}