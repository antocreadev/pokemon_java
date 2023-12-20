package cli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pokedex {

  static ArrayList<Pokemon> pokedex = new ArrayList<>();

  public Pokedex() {
    try {
      loadPokemon();
      System.out.println("[LOG] Loaded " + pokedex.size() + " pokemons");
    } catch (FileNotFoundException e) {
      System.out.println("[ERROR] " + e.getMessage());
      System.exit(1);
    } catch (IOException e) {
    }
  }

  /**
   * Load all existing pokemons from a csv file into the pokedex.
   * 
   * @throws IOException
   * @throws FileNotFoundException
   */
  private static void loadPokemon() throws FileNotFoundException, IOException {
    try {
      // Reading CSV file
      BufferedReader br = new BufferedReader(new FileReader(Main.POKEMONS_PATH));
      String line;

      // Skip the header line
      br.readLine();

      // Read each line, create a Pokemon object and add it to the temporary map
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        int id = Integer.parseInt(values[0]);
        String name = values[1];
        int hp = Integer.parseInt(values[2]);
        int attack = Integer.parseInt(values[3]);
        PokemonType type = PokemonType.valueOf(values[4].toUpperCase());
        int evolutionStage = Integer.parseInt(values[5]);
        String evolutionName = values[6];

        Pokemon pokemon = new Pokemon(id, name, hp, attack, type, evolutionStage, null, evolutionName);
        pokedex.add(pokemon);
      }

      // Set the evolution for each Pokemon
      for (Pokemon pokemon : pokedex) {
        setEvolutionChain(pokemon);
      }

      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void setEvolutionChain(Pokemon pokemon) {
    String evolutionName = pokemon.getEvolutionName();
    if (evolutionName != null && !evolutionName.equals("NULL")) {
      String[] evolutionNames = evolutionName.split(";");
      if (evolutionNames.length > 1) {
        for (String name : evolutionNames) {
          Pokemon evolution = getPokemonByName(name);
          pokemon.addEvolution(evolution);
        }
        return;
      } else {
        Pokemon evolution = getPokemonByName(evolutionNames[0]);
        pokemon.addEvolution(evolution);
      }
    }
  }

  public static ArrayList<Pokemon> getPokedex() {
    return pokedex;
  }

  public static void setPokedex(ArrayList<Pokemon> pokedex) {
    Pokedex.pokedex = pokedex;
  }

  /**
   * Get a new pokemon from the pokedex by its name
   * 
   * @param name
   * @return A new Pokemon object
   */
  public static Pokemon getPokemonByName(String name) {
    for (Pokemon pokemon : pokedex) {
      if (pokemon.getName().equals(name)) {
        return new Pokemon(pokemon);
      }
    }
    return null;
  }

}
