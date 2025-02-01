package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class VineWhip extends PhysicalMove{
    public VineWhip (double pow, double acc){
        super(Type.GRASS, pow, acc);
    }

    @Override
    protected String describe(){
        return "атакует вином";
    }
    
}
