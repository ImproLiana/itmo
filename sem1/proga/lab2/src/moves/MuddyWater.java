package moves;

import ru.ifmo.se.pokemon.*;

public class MuddyWater extends SpecialMove{
    public MuddyWater (double pow, double acc){
        super(Type.WATER, pow, acc);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        super.applyOppEffects(p);
        Effect e = new Effect().chance(0.3).turns(1).stat(Stat.ACCURACY, -1);  
        p.addEffect(e);
    }

    @Override
    protected String describe(){
        return "атакует водой";
    }
    
}
