import java.util.ArrayList;

/**
 * Pokemon
 */
public class Pokemon {

    private String name;
    private String type; 
    private Integer pv; 
    private Float appearanceRate;
    private ArrayList<Pokemon> evolution;


    // constructor
    public Pokemon(String type, Integer pv, Float appearanceRate, String name, ArrayList<Pokemon> evolution) {
        this.name = name;
        this.type = type;
        this.pv = pv;
        this.appearanceRate = appearanceRate;
        if (evolution != null){
            this.evolution = evolution;
        }
    } 

    public Pokemon(String type, Integer pv, Float appearanceRate, String name) {
        this(type, pv, appearanceRate, name, null);
    }


    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Float getAppearanceRate() {
        return appearanceRate;
    }

    public void setAppearanceRate(Float appearanceRate) {
        this.appearanceRate = appearanceRate;
    }

    public ArrayList<Pokemon> getEvolution() {
        return evolution;
    }

    public void setEvolution(ArrayList<Pokemon> evolution) {
        this.evolution = evolution;
    }

    // override toString
    @Override
    public String toString() {
        return name +" [type=" + type + ", pv=" + pv + ", appearanceRate=" + appearanceRate
                + ", evolution=" + evolution + "]";
    } 
    public static void main(String[] args) {
        Pokemon pikachu = new Pokemon("electrique", 100, 0.5f, "pikachu");
        Pokemon raichu = new Pokemon("electrique", 100, 0.5f, "raichu");
        ArrayList<Pokemon> evPikachu = new ArrayList<Pokemon>();
        evPikachu.add(raichu);
        pikachu.setEvolution(evPikachu);

        System.out.println(pikachu);
    }


}