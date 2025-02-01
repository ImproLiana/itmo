package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class FeintAttack extends PhysicalMove{
    public FeintAttack (double pow, double acc){
        super(Type.DARK, pow, acc);
    }

    @Override
    protected String describe(){
        // return describe() + "lala";
        return "атакует Феном";
    }
    
}
