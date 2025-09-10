package progclasses;

public class Coordinates {
    private long x; //Максимальное значение поля: 511
    private long y;

    public Coordinates(long x, long y){
        if (x > 511){
            System.out.println("(НЕ ДОЛЖНО БЫТЬ)Значение переменной x слишком большое.");
        } else {
            this.x = x;
            this.y = y;
        }
    }

    public long getX(){
        return this.x;
    }

    public long getY(){
        return this.y;
    }

    @Override
    public String toString(){
        return "{" + this.x + ", " + this.y + "}";
    }

    public String values(){
        return this.x + "," + this.y;
    }
}