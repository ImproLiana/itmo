package Characters;

import java.util.Random;

import Exceptions.DeathException;
import Instruments.MainCharacter;
import Person.Person;
import Things.*;


public class Duchess extends Person implements MainCharacter{

    private Portrait duchessPortrait;

    public Duchess(String name){
        super(name);
    }
    @Override
    public void sleep(){
        System.out.println("Герцогиня неожиданно засыпает.");
        this.setReputation(this.getReputation() - 15);
        this.setAngry(this.getAngry() + 10);
    }
    public void drink(Vine vine){
        System.out.println("Герцогиня выпивает немного вина.");
        this.setHappy(this.getHappy() + vine.getDegree());
    }
    @Override
    public void eat(){
        System.out.println("Герцогиня начинает свою трапезу.");
        this.setHappy(this.getHappy()  + 5);
    }
    @Override
    public void dance(){
        System.out.println("Герцигиня выходит в центр зала и начинает танцевать.");
        this.setReputation(this.getReputation() + 10);
        this.setHappy(this.getHappy() + 5);
    }
    @Override
    public void talkTo(Person person) throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        System.out.println("Герцогиня начинает диалог с гостем по имени " + person.getName() + ".");
        person.setReputation(getReputation()+30);
    }

    public void setDuchess_portrait(Portrait portrait){
        this.duchessPortrait = portrait;
    }

    public Portrait setDuchess_portrait(){
        return this.duchessPortrait;
    }

    public void smth(Vine vine) throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        Random n = new Random();
        int i = n.nextInt(4)+1;
        if (i == 1){
            this.sleep();
        } else if (i == 2){
            this.drink(vine);
        } else if (i == 3){
            this.eat();
        } else if(i == 4){
            this.dance();
        }
    }

}
