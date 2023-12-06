import java.util.ArrayList;

public class Dresseur {
    private String name;
    private ArrayList<Pokemon> pokemons;
    public final static Integer nbMaxPokemon = 6;
    
    // constructor
    public Dresseur(String name, ArrayList<Pokemon> pokemons) {
        this.name = name;
        this.pokemons = pokemons;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public static Integer getNbmaxpokemon() {
        return nbMaxPokemon;
    }

    // methods
    public void addPokemon(Pokemon pokemon) {
        if (this.pokemons.size() < nbMaxPokemon) {
            this.pokemons.add(pokemon);
        } else {
            System.out.println("Vous avez déjà 6 pokémons");
        }
    }

    public void removePokemon(Pokemon pokemon) {
        this.pokemons.remove(pokemon);
    }

    
}
