package progclasses;

public enum DragonType {
    WATER{
        @Override
        public String toString(){
            return "water";
        }
    },
    UNDERGROUND{
        @Override
        public String toString(){
            return "underground";
        }
    },
    AIR{
        @Override
        public String toString(){
            return "air";
        }
    },
    FIRE{
        @Override
        public String toString(){
            return "fire";
        }
    };

    public static DragonType set(String user_type){
        for (DragonType type: DragonType.values()){
            if (type.toString().equalsIgnoreCase(user_type)){
                return type;
            }
        }
        throw new IllegalArgumentException("Нет такого типа");
    }
}