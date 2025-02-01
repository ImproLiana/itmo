package moves;

import ru.ifmo.se.pokemon.*;

public class RockPolish extends StatusMove{
    public RockPolish (double pow, double acc){
        super(Type.ROCK, pow, acc);
    }

    @Override
    protected void applySelfEffects(Pokemon p){
        super.applySelfEffects(p);
        Effect e = new Effect().stat(Stat.SPEED, 2);
        p.addEffect(e);
    }
    
    @Override
    protected String describe(){
        return "атакует камнем";
    }
}
