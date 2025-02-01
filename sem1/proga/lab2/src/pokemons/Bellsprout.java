package pokemons;

import moves.SleepPowder;
import moves.VineWhip;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Bellsprout extends Pokemon {
    public Bellsprout(String name, int level){
        super(name, level);
        super.setStats(50, 75, 35, 70, 30, 40);
        super.setType(Type.POISON, Type.GRASS);
        super.setMove(new VineWhip(45, 100), new SleepPowder(0, 75));
    }
}
