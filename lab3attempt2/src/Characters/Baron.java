package Characters;

import java.util.Random;

import Exceptions.DeathException;
import Instruments.Crowd;
import Person.Person;

public class Baron extends Person implements Crowd{

    public Baron(String name){
        super(name);
    }

    @Override
    public void watching(Person person, Person duchess) throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        if (person.getAlive() == false){
            throw new DeathException(person);
        }
        if (duchess.getAlive() == false){
            throw new DeathException(duchess);
        }
        person.setHappy(person.getHappy()+10);
        person.setReputation(getReputation()+10);
        System.out.println("Барон " + this.getName() + " обратил свое внимание на гостя под именем " + person.getName() + ".");
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
        String[] acts = {" разбивает ценную вазу", " громко бранится", " пытается начать драку", " начинает танцевать лезгинку", " зачтывает рэп"};
        Random n = new Random();
        int rand_int = n.nextInt(5);
        System.out.println("Барон " + this.name + acts[rand_int] + ".");
    }

    @Override
    public void actNice() throws DeathException{
        if (this.getAlive() == false){
            throw new DeathException(this);
        }
        this.setReputation(getReputation() + 5);
        String[] acts = {" танцеут вальс", " демонстрирует наряды", " предлогает сыграть в карточную игру", " обсуждает литературу", " жертвует на благотворительность"};
        Random n = new Random();
        int rand_int = n.nextInt(5);
        System.out.println("Барон " + this.name + acts[rand_int] + ".");
    }
}
