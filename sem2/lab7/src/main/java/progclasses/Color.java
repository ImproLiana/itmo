package progclasses;

import java.io.Serializable;

public enum Color implements Serializable{
    BLACK{
        @Override
        public String toString(){
            return "BLACK";
        }
    },
    ORANGE{
        @Override
        public String toString(){
            return "ORANGE";
        }
    },
    WHITE{
        @Override
        public String toString(){
            return "WHITE";
        }
    },
    BROWN{
        @Override
        public String toString(){
            return "BROWN";
        }
    };

    public static Color set(String user_color){
        for (Color color: Color.values()) {
            if (color.toString().equalsIgnoreCase(user_color)){
                return color;
            }
        }
        throw new IllegalArgumentException("Такого цвета нет");
    }


}