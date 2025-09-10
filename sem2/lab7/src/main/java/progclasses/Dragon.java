package progclasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.SplittableRandom;

public class Dragon implements Comparable<Dragon>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long age; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле не может быть null
    private Person killer; //Поле может быть null
    private String owner;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public void setKiller(Person killer) {
        this.killer = killer;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId(){
        return this.id;
    }

    public DragonCharacter getCharacter(){
        return this.character;
    }

    public Color getColor(){
        return this.color;
    }


    @Override
    public String toString() {
        return "=============================\n" +
                "id: " + this.id + "\n" +
                "name: " + this.name + "\n" +
                "coordinates: " + this.coordinates + "\n" +
                "creation_date: " + this.creationDate + "\n" +
                "age: " + this.age + "\n" +
                "color: " + this.color + "\n" +
                "type: " + Objects.toString(this.type, "none") + "\n" +
                "character: " + this.character + "\n" +
                "killer: " + Objects.toString(this.killer, "none") + "\n" +
                "owner: " + this.owner;

    }
    

    public String values() {
        return this.id + "," + this.name + "," + this.coordinates.values() + "," + this.creationDate + "," + this.age + ","
                + this.color + "," + (this.type == null ? "none" : this.type) + "," + this.character + ","
                + (this.killer == null ? "none" : this.killer.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return Objects.equals(id, dragon.id) &&
                Objects.equals(name, dragon.name) &&
                Objects.equals(coordinates, dragon.coordinates) &&
                Objects.equals(creationDate, dragon.creationDate) &&
                Objects.equals(age, dragon.age) &&
                Objects.equals(color, dragon.color) &&
                Objects.equals(type, dragon.type) &&
                Objects.equals(character, dragon.character) &&
                Objects.equals(killer, dragon.killer);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Dragon other) {
        if (other == null) {
            throw new IllegalArgumentException("Нельзя сравнить с пустым значением");
        }
        return Long.compare(this.age, other.age);
    }


    public double calculateDistanceFromOrigin() {
        return Math.hypot(this.coordinates.getX(), this.coordinates.getY());
    }

    public long getAge() {
        return this.age;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public DragonType getType() {
        return type;
    }

    public Person getKiller() {
        return killer;
    }
}
