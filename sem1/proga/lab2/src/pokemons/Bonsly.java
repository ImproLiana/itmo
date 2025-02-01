package pokemons;

import moves.FeintAttack;
import moves.Headbutt;
import moves.RockPolish;
import ru.ifmo.se.pokemon.*;

public class Bonsly extends Pokemon{
    public Bonsly (String name, int level) {
        super(name, level);
        super.setStats(50, 80, 95, 10, 45, 10);
        super.setType(Type.ROCK);   
        super.setMove(new Headbutt(70, 100), new FeintAttack(60, 100), new RockPolish(0, 100));
    }

    
}