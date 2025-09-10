package progclasses;

public class Location {
    private long x;
    private Double y; //Поле не может быть null
    private long z;
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Location(long x, Double y, long z, String name){
        if (y == null || name == null){
            System.out.println("(!!!)Значения не могут быть пустыми.");
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
            this.name = name;
        }
    }
    @Override
    public String toString(){
        return "{" + this.x + ", " + this.y + ", " + this.z + ", location name: " + this.name + "}";
    }

    public String values(){
        return this.x + "," + this.y + "," + this.z + "," + this.name;
    }

    public Double getY() {
        return y;
    }

    public long getX() {
        return x;
    }

    public long getZ() {
        return z;
    }

    public String getName() {
        return name;
    }
}
