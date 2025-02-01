package pokemons;

import moves.PoisonPowder;
import moves.SleepPowder;
import moves.VineWhip;
// import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Weepinbell extends Bellsprout {
    public Weepinbell(String name, int level) {
        super(name, level);
        super.setStats(65, 90, 50, 85, 45, 55);
        super.setType(Type.POISON, Type.GRASS);
        super.setMove(new VineWhip(45, 100), new SleepPowder(0, 75), new PoisonPowder(0, 75));
    }
}
