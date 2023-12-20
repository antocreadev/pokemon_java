package cli;

public enum PokemonType {
  NORMAL,
  FIRE,
  WATER,
  GRASS,
  ELECTRIC,
  ICE,
  FIGHTING,
  POISON,
  GROUND,
  FLYING,
  PSYCHIC,
  BUG,
  ROCK,
  GHOST,
  DRAGON,
  DARK,
  STEEL;

  public String getEmojiType() {
    switch (this) {
      case NORMAL:
        return "🌐";
      case FIRE:
        return "🔥";
      case WATER:
        return "💧";
      case GRASS:
        return "🌿";
      case ELECTRIC:
        return "⚡";
      case ICE:
        return "❄️";
      case FIGHTING:
        return "🥊";
      case POISON:
        return "☠️";
      case GROUND:
        return "⛰️";
      case FLYING:
        return "🦅";
      case PSYCHIC:
        return "🔮";
      case BUG:
        return "🐛";
      case ROCK:
        return "🪨";
      case GHOST:
        return "👻";
      case DRAGON:
        return "🐉";
      case DARK:
        return "🌑";
      case STEEL:
        return "🔩";
      default:
        return "⭕️";
    }
  }
}
