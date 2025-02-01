package Characters;
import java.util.Random;

import Exceptions.DeathException;
import Instruments.MainCharacter;
import Person.Person;
import Things.*;

public class Princess extends Person implements MainCharacter{

    public Princess(String name){
        super(name);
    }
    
    @Override
    public void sleep(){
        System.out.println("Ко всеобщему удивленияю принцесса засыпает во время разгара бала.");
        this.setReputation(this.getReputation() - 10);
        this.setAngry(this.getAngry() + 20);
    }
    public void drink(Vine vine){
        System.out.println("Принцесса решает отведать немного вина.");
        this.setHappy(this.getHappy() + vine.getDegree());
    }
    @Override
    public void eat(){
        System.out.println("Принцесса пробует блюда от лучших шефов.");
        this.setHappy(this.getHappy()  + 5);
    }
    @Override
    public void dance(){
        System.out.println("Принцесса начинает свой невероятный танец.");
        this.setReputation(this.getReputation() + 20);
        this.setHappy(this.getHappy() + 10);
    }
    @Override
    public void talkTo(Person person) throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        System.out.println("Принцесса оказывает честь гостю по имени " + person.getName() + " , начиная с ним разговор.");
        person.setReputation(getReputation() + 20);
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
