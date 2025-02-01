package moves;

import ru.ifmo.se.pokemon.*;

public class LeafTornado extends SpecialMove{
    public LeafTornado(double pow, double acc){
        super(Type.GRASS, pow, acc);
    }
    
    @Override
    protected void applyOppEffects(Pokemon p){
        super.applyOppEffects(p);
        Effect e = new Effect().chance(0.3).stat(Stat.ACCURACY, -1);
        p.addEffect(e);
    }

    @Override
    protected String describe(){
        return "атакует листвой";
    }
}
