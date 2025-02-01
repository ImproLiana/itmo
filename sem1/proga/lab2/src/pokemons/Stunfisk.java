package pokemons;

import moves.MuddyWater;
import moves.RockTomb;
import moves.ShockWave;
import moves.Thunderbolt;
import ru.ifmo.se.pokemon.*;

public final class Stunfisk extends Pokemon{
    public Stunfisk (String name, int level) {
        super(name, level);
        super.setStats(109, 66, 84, 81, 99, 32);
        super.setType(Type.GROUND, Type.ELECTRIC);
        super.setMove(new MuddyWater(90, 85), new ShockWave(60, 100), new RockTomb(60, 95), new Thunderbolt(90, 100));
        
    }
    
}