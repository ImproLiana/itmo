package moves;

import ru.ifmo.se.pokemon.*;


public class WoodHammer extends PhysicalMove{
    public WoodHammer (double pow, double acc){
        super(Type.GRASS, pow, acc);
    }

    // @Override
    // protected void applySelfDamage(Pokemon p){
    //     // super.applySelfDamage(p);
        
    //     p.applySelfDamage();
    // }

    // @Override
    // protected void applySelfEffects(Pokemon p){
    //     super.applySelfEffects(p);
    //     Effect e = new Effect().stat(Stat.HP, -10);
    //     p.addEffect(e);
    // }

    @Override
    protected String describe(){
        return "атакует деревянным молотом"; 
    }
    
}
