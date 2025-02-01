package Things;

import Exceptions.DeathException;
import Person.Person;

public class Trone {
    private int gold;

    public int getGold(){
        return gold;
    }

    public void setGold(int gold){
        this.gold = gold;
    }

    public void seat(Person p) throws DeathException{
        if (p.getAlive() == false){
            throw new DeathException(p);
        }
        if (this.gold > 0){
            p.setHappy(p.getHappy()+this.gold);
            System.out.println(p.getName() + " подмечает, что такое почетное место прекрасно.");
        } else {
            p.setAngry(p.getAngry()+Math.abs(this.gold));
            System.out.println(p.getName() + " подмечает, что такое почетное место ужасно.");
        }
    }
}
