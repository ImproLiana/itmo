package moves;

import java.util.Random;

import ru.ifmo.se.pokemon.*;

public class Headbutt extends PhysicalMove{
    public Headbutt(double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        super.applyOppEffects(p);
        Random r = new Random();
        int x = r.nextInt(11);
        if (x <= 3){
            Effect.flinch(p);
        }
    }

    @Override
    protected String describe(){
        return "атакует бутоном";
    }
    
}
