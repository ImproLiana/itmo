package Characters;

import java.util.Random;

import Exceptions.DeathException;
import Instruments.Crowd;
import Person.Person;

public class Duke extends Person implements Crowd{

    public Duke(String name){
        super(name);
    }

    @Override
    public void watching(Person person, Person duchess) throws DeathException{
        if (person.getAlive() == false){
            throw new DeathException(person);
        }
        if (duchess.getAlive() == false){
            throw new DeathException(duchess);
        }
        person.setReputation(getReputation()+10);
        person.setHappy(person.getHappy() + this.getReputation()+10);
        System.out.println("Герцог " + this.getName() + " удостоил своим вниманием гостя под именем " + person.getName() + ".");
        if (person.getName() != "Грудж"){
            duchess.setHappy(getHappy() + this.getReputation()*2);
        } else if (person.getName() == "Грейс"){
            duchess.setAngry(getAngry() + 10 + this.getReputation()*2);
        }
    }

    @Override
    public void actBad() throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        this.setReputation(getReputation() - 5);
        String[] acts = {" плюет в тарелки", " отбирает еду", " ставит подноджки", " портит нряды гостей", " пинает гостей"};
        Random n = new Random();
        int rand_int = n.nextInt(5);
        System.out.println("Герцог " + this.name + acts[rand_int] + ".");
    }

    @Override
    public void actNice() throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        this.setReputation(getReputation() + 5);
        String[] acts = {" наслаждается музыкой", " активно дискуссирует", " подправляет пиджак", " обсуждает искусство", " читает по памяти великие поэмы"};
        Random n = new Random();
        int rand_int = n.nextInt(5);
        System.out.println("Герцог " + this.name + acts[rand_int] + ".");
    }
}
