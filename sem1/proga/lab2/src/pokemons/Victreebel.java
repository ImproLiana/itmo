package pokemons;

import moves.LeafTornado;
import moves.PoisonPowder;
import moves.SleepPowder;
import moves.VineWhip;
import ru.ifmo.se.pokemon.Type;

public final class Victreebel extends Weepinbell {
    public Victreebel(String name, int level){
        super(name, level);
        super.setStats(80, 105, 65, 100, 70, 70);
        super.setType(Type.POISON, Type.GRASS);
        super.setMove(new VineWhip(45, 100), new SleepPowder(0, 75), new PoisonPowder(0, 75), new LeafTornado(65, 90));
    }
}
