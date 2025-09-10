package progclasses;

public enum Color {
    BLACK{
        @Override
        public String toString(){
            return "black";
        }
    },
    ORANGE{
        @Override
        public String toString(){
            return "orange";
        }
    },
    WHITE{
        @Override
        public String toString(){
            return "white";
        }
    },
    BROWN{
        @Override
        public String toString(){
            return "brown";
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