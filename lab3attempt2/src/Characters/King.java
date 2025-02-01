package Characters;

import java.util.Random;

import Exceptions.DeathException;
import Instruments.MainCharacter;
import Person.Person;


import Things.Portrait;
import Things.Vine;

public class King extends Person implements MainCharacter{


    public King (String name){
        super(name);
    }
    @Override
    public void sleep(){
        System.out.println("Король от скуки засыпает.");
        this.setReputation(this.getReputation() - 15);
        this.setAngry(this.getAngry() + 5);
    }
    public void drink(Vine vine){
        System.out.println("Не удивительно, что король начинает выпивать.");
        this.setReputation(this.getReputation() + 10);
        this.setHappy(this.getHappy() + vine.getDegree());
    }
    @Override
    public void eat(){
        System.out.println("Король конечно не пропускает ужин, пробуя лучшую еду данного мероприятия.");
        this.setReputation(this.getReputation() + 15);
        this.setHappy(this.getHappy()  + 5);
    }
    @Override
    public void dance(){
        System.out.println("Король пускается в пляс, чем поражает весь зал.");
        this.setReputation(this.getReputation() - 10);
        this.setHappy(this.getHappy() + 5);
    }
    @Override
    public void talkTo(Person person){
        System.out.println("Король затевает разговор с гостем по имени" + person.getName() + ".");
        person.setReputation(getReputation() + 40);
    }

    public void present_portrait(Duchess duchess, Portrait portrait) throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        System.out.println("Король " + this.getName() + " вручает " + duchess.getName() + " ее портрет. Они вхоядт в зал.");
        duchess.setDuchess_portrait(portrait);
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
