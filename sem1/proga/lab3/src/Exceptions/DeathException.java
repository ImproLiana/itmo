package Exceptions;

import Person.Person;

public class DeathException extends Exception{
    public DeathException(Person person){
        super(person.getName() + " погиб(ла)..");
    }
}
