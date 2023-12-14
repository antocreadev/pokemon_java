import java.util.ArrayList;

public class Dresseur {
    public int playerX = 90;
    public int playerY = 90;

    private Sprite playerSprite;

    private String name;
    private ArrayList<Pokemon> pokemons;
    public final static Integer nbMaxPokemon = 6;
    
    // constructor
    public Dresseur(String name, ArrayList<Pokemon> pokemons) {
        this.name = name;
        this.pokemons = pokemons;
        this.playerSprite = new Sprite("assets/img/character/", 2, "down");
    }

    // getters and setters
    public Sprite getPlayerSprite() {
        return playerSprite;
    }

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

    // incrementation position
    public void incrementPlayerX(int x) {
        this.playerX += x;
    }

    public void incrementPlayerY(int y) {
        this.playerY += y;
    }
    
}
