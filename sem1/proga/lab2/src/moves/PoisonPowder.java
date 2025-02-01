package moves;

import ru.ifmo.se.pokemon.*;

public class PoisonPowder extends StatusMove{
    public PoisonPowder(double pow, double acc){
        super(Type.POISON, pow, acc);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        super.applyOppEffects(p);
        Effect.poison(p);
    }

    @Override
    protected String describe(){
        return "атакует пыльцой";
    }
    
}
