package Instruments;

import Exceptions.DeathException;
import Person.Person;

public interface Crowd{
    void watching(Person p, Person d) throws DeathException;
    void actBad() throws DeathException;
    void actNice() throws DeathException;

}
