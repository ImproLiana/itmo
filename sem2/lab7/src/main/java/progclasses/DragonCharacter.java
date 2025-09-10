package progclasses;

public enum DragonCharacter {
    CUNNING{
        @Override
        public String toString(){
            return "cunning";
        }
    },
    WISE{
        @Override
        public String toString(){
            return "wise";
        }
    },
    CHAOTIC{
        @Override
        public String toString(){
            return "chaotic";
        }
    },
    CHAOTIC_EVIL{
        @Override
        public String toString(){
            return "chaotic_evil";
        }
    },
    FICKLE{
        @Override
        public String toString(){
            return "fickle";
        }
    };
    public static DragonCharacter set(String user_character){
        for (DragonCharacter character: DragonCharacter.values()) {
            if (character.toString().equalsIgnoreCase(user_character)){
                return character;
            }
        }
        throw new IllegalArgumentException("Такого характера нет");
    }

    public int compareLength(DragonCharacter other) {
        return this.toString().length() - other.toString().length();
    }
}
