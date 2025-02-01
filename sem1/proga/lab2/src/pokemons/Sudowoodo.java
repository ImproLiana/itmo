package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public final class Sudowoodo extends Bonsly{
    public Sudowoodo(String name, int level) {
        super(name, level);
        super.setStats(70, 100, 115, 30, 65, 30);
        super.setType(Type.ROCK);
        super.setMove(new Headbutt(70, 100), new FeintAttack(60, 100), new RockPolish(0, 100), new WoodHammer(120, 100));

    }

}
