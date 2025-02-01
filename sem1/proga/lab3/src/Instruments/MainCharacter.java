package Instruments;
import Exceptions.DeathException;
import Person.Person;

public interface MainCharacter{
    void dance() throws DeathException;
    void eat() throws DeathException;
    void sleep() throws DeathException;
    void talkTo(Person person) throws DeathException;
} 
