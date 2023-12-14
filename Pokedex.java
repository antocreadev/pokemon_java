import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pokedex {
  static ArrayList<Pokemon> pokedex = new ArrayList<>();

  public Pokedex() {
    try {
      loadPokemon();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      System.out.println("IO Exception");
    }
    System.out.println("[LOG] Loaded " + pokedex.size() + " pokemons");
  }

  /**
   * Load all existing pokemons from a csv file into the pokedex.
   * 
   * @throws IOException
   * @throws FileNotFoundException
   */
  private static void loadPokemon() throws FileNotFoundException, IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("./assets/data/pokemons.csv"))) {
      String line;
      boolean firstLine = true;
      while ((line = br.readLine()) != null) {
        if (firstLine) {
          firstLine = false;
          continue;
        }
        // Create a new Pokemon for each line and add it to the Pokedex
        String[] data = line.split(",");
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        int hp = Integer.parseInt(data[2]);
        int attack = Integer.parseInt(data[3]);
        String type = data[4];
        int evolutionStage = Integer.parseInt(data[5]);
        String evolutionString = data[6];   
        ArrayList<Pokemon> evolution = new ArrayList<>();
        if (!evolutionString.equals("NULL")) {
            String[] evolutionArray = evolutionString.split(";");
            for (String evolutionName : evolutionArray) {
                evolution.add(findPokemonByName(evolutionName));
            }
        }
        Pokemon pokemon = new Pokemon(id, name, hp, attack, PokemonType.valueOf(type.toUpperCase()), evolutionStage,
            evolution);
        pokedex.add(pokemon);
      }
    }
  }

  // methode find in csv pokemon by name return pokemon
  public static Pokemon findPokemonByName(String name) throws FileNotFoundException, IOException{
    Pokemon wildPokemon = null;
    // read csv file
    try (BufferedReader br = new BufferedReader(new FileReader("./assets/data/pokemons.csv"))) {
      String line;
      boolean firstLine = true;
      while ((line = br.readLine()) != null) {
        if (firstLine) {
          firstLine = false;
          continue;
        }
        // Create a new Pokemon for each line and add it to the Pokedex
        String[] data = line.split(",");
        int id = Integer.parseInt(data[0]);
        String namePokemon = data[1];
        int hp = Integer.parseInt(data[2]);
        int attack = Integer.parseInt(data[3]);
        String type = data[4];
        int evolutionStage = Integer.parseInt(data[5]);
        // String evolutionString = data[6];
        ArrayList<Pokemon> evolution = new ArrayList<>();
   
        if (namePokemon.equals(name)) {
        //     if (!evolutionString.equals("NULL")) {
        //     String[] evolutionArray = evolutionString.split(";");
        //     for (String evolutionName : evolutionArray) {
        //         evolution.add(findPokemonByName(evolutionName));
        //     }
        // }
          wildPokemon = new Pokemon(id, name, hp, attack, PokemonType.valueOf(type.toUpperCase()), evolutionStage, evolution);
        }
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      System.out.println("IO Exception");
    }
    return wildPokemon;
  }

  public static ArrayList<Pokemon> getPokedex() {
    return pokedex;
  }

  public static void setPokedex(ArrayList<Pokemon> pokedex) {
    Pokedex.pokedex = pokedex;
    
  }
  
// public static void main(String[] args) {
//     Pokedex pokedex = new Pokedex();
//     // boucle pour afficher tous les pokemons
//     for (Pokemon pokemon : pokedex.getPokedex()) {
//       System.out.println(pokemon);
//     }
// }
}