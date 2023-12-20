package lib;

/**
 * CaughtPokemonEnum is an enum class that represents the result status
 * of catching a pokemon
 * 
 * <p>
 * FAILED (-1): The trainer's team is full
 * </p>
 * <p>
 * ESCAPED (0): The pokemon escaped
 * </p>
 * <p>
 * SUCCESS (1): The pokemon is caught
 * </p>
 * <p>
 * SPECIAL (2): The pokemon is caught and the trainer gets a rare candy
 * </p>
 */
public enum PokemonCaughtResultEnum {

  FAILED(-1),
  ESCAPED(0),
  SUCCESS(1),
  SPECIAL(2);

  private int value;

  PokemonCaughtResultEnum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
