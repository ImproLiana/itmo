import pokemons.Bellsprout;
import pokemons.Bonsly;
import pokemons.Stunfisk;
import pokemons.Sudowoodo;
import pokemons.Victreebel;
import pokemons.Weepinbell;
import ru.ifmo.se.pokemon.*;

public class App {
public static void main(java.lang.String[] args){
    Battle bat = new Battle();
    Pokemon p1 = new Stunfisk("Ирис", 6);
    Pokemon p2 = new Bonsly("Микки", 4);
    Pokemon p3 = new Sudowoodo("Вася", 9);
    Pokemon p4 = new Bellsprout("Стеф", 5);
    Pokemon p5 = new Weepinbell("Луна", 3); 
    Pokemon p6 = new Victreebel("Дарси", 2); 
    bat.addAlly(p1);
    bat.addAlly(p2);
    bat.addAlly(p3);
    bat.addFoe(p4);
    bat.addFoe(p5);
    bat.addFoe(p6);
    bat.go();
}
}
