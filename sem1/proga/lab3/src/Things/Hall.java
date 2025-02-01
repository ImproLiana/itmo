package Things;

import java.util.ArrayList;

import Person.Person;

public class Hall {
    private int quality;
    private ArrayList<Person> guests;


    public Hall() {
        guests = new ArrayList<>(); 
    }


    public ArrayList<Person> getGuests(){
        return guests;
    }

    public void setGuests(ArrayList<Person> guests){
        for (Person i : guests) {
            this.guests.add(i);
        }
    }

    public int getQuality(){
        return quality;
    }

    public void setQuality(int quality){
        this.quality = quality;
    }

    public void getOnFire(Person p){
        p.setAlive(false);
        String g = "";
        for (Person i : guests) {
            i.setAlive(false);
            g += i.getName() + ", ";
        }
        System.out.println(p.getName() + " поджигает зал и погибает...");
        System.out.println(g + "погибшие при пожаре, покидают наш мир.");

    }

    public void cameIn(Person p){
        if (this.getQuality() > 0){
            p.setHappy(p.getHappy()+this.getQuality());
            System.out.println(p.getName() + " говорит что зал выглядит великолепно.");
        } else {
            p.setAngry(p.getAngry()+Math.abs(this.getQuality()));
            System.out.println(p.getName() + " говорит что зал омерзителен.");
        }
        
    }
}
