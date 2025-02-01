package Person;

import java.util.Objects;

public abstract class Person{
    public int reputation;
    public boolean alive;
    public int happy;
    public int angry;
    public String name;

    public Person(int reputation, boolean alive, int happy, int angry, String name){
        this.reputation = reputation;
        this.alive = alive;
        this.happy = happy;
        this.angry = angry;
        this.name = name;
    }

    public Person(){
        this.reputation = 0;
        this.alive = true;
        this.happy = 0;
        this.angry = 0;
        this.name = "";
    }

    public Person(String name){
        this.reputation = 0;
        this.alive = true;
        this.happy = 0;
        this.angry = 0;
        this.name = name;
    }

    public int getReputation() {
        return this.reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getHappy() {
        return this.happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getAngry() {
        return this.angry;
    }

    public void setAngry(int angry) {
        this.angry = angry;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        Person person = (Person) obj; 
        return name == person.getName() && reputation == person.getReputation();
    }


    @Override
    public int hashCode(){
        return Objects.hash(name, reputation);
    }

    @Override
    public String toString(){
        return "Person[name = " + this.getName() + ", reputation = " + this.getReputation() + ", angry = " + this.getAngry() + ", happy = " + this.getHappy();
    }
}
