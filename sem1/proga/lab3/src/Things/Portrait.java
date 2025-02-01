package Things;

import java.util.Random;

import Person.Person;

public class Portrait {
    private int beauty;
    private Style style;

    public int getBeauty(){
        return beauty;
    }

    public void setBeauty(int beauty){
        this.beauty = beauty;
    }
    public void broken(Person p){
        p.setAngry(100);
    }

    protected enum Style{
        FULL_LENGTH, LIFESTYLE, TRADITIONAL, SELF_PORTRAIT 
    }


    public void setStyle(){
        Random r = new Random();
        int rand_int = r.nextInt(4);
        
        this.style = Style.values()[rand_int];
    }
    
    public Style getStyle() {
        return style; 
    }

}

