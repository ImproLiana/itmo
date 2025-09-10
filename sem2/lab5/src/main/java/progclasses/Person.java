package progclasses;
import java.util.Objects;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Длина строки должна быть не меньше 10, Длина строки не должна быть больше 32, Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, String passportID, Location location){
            this.name = name;
            this.passportID = passportID;
            this.location = location;
    }

    @Override
    public String toString(){
        return "{name: " + this.name + ", passwordID: " + Objects.toString(this.passportID, "none") + ", location: " + this.location.toString() + "}";
    }

    public String values(){
        return this.name + "," + Objects.toString(this.passportID, "none") + "," + this.location.values();
    }

   public String getName(){
        return this.name;
   }

   public String getPassportID(){
        return this.passportID;
   }

    public Location getLocation() {
        return this.location;
    }
}
