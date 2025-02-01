package moves;

import ru.ifmo.se.pokemon.*;

public class ShockWave extends SpecialMove{
    public ShockWave (double pow, double acc){
        super(Type.ELECTRIC, pow, acc);
    }
    
    @Override
    protected String describe(){
        return "атакует волнами";
    }


}
