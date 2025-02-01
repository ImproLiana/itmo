package moves;

import java.util.Random;

import ru.ifmo.se.pokemon.*;

public class SleepPowder extends StatusMove{
    public SleepPowder(double pow, double acc){
        super(Type.GRASS, pow, acc);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Random r = new Random();
        int x = r.nextInt(3)+1;
        super.applyOppEffects(p);
        Effect e = new Effect().turns(x).condition(Status.SLEEP);
        p.setCondition(e); 

    }

    @Override
    protected String describe(){
        return "использует слипи пыль";
    }
}
