package functions;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import progclasses.*;

/**
 * Класс для интерактивного заполнения объекта Dragon.
 * Обеспечивает пошаговый ввод всех полей дракона с валидацией данных.
 */
public class FillObject implements Serializable {
    private static final Scanner scanner = new Scanner(System.in);

    public static void fill(Dragon dragon) {
        dragon.setCreationDate(LocalDate.now());

        dragon.setName(getNotEmptyString("Введите имя дракона: "));
        dragon.setCoordinates(getCoordinates());
        dragon.setAge(getLong("Введите возраст дракона: "));
        dragon.setColor(getColor());
        dragon.setCharacter(getDragonCharacter());
        dragon.setType(getType());

        System.out.println("\nТеперь нужны данные человека, убившего дракона (если есть).");
        dragon.setKiller(getPerson());
    }

    private static String safeInput(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("\nВвод прерван. Завершение работы.");
            System.exit(0);
            return null;
        }
    }

    private static String getNotEmptyString(String prompt) {
        while (true) {
            String input = safeInput(prompt);
            if (!input.isEmpty()) return input;
            System.out.println("Значение не может быть пустым!");
        }
    }

    private static long getLong(String prompt) {
        while (true) {
            try {
                String input = safeInput(prompt);
                long value = Long.parseLong(input);
                if (value > 0) return value;
                System.out.println("Значение должно быть больше 0!");
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static Coordinates getCoordinates() {
        System.out.println("\nВведите координаты дракона:");
        long x = getX();
        long y = getY();
        return new Coordinates(x, y);
    }

    private static long getX() {
        while (true) {
            try {
                String input = safeInput("Координата X (макс. 511): ");
                long x = Long.parseLong(input);
                if (x <= 511) return x;
                System.out.println("Максимальное значение X: 511!");
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static long getY() {
        while (true) {
            try {
                String input = safeInput("Координата Y: ");
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static Color getColor() {
        return getRequiredEnum("Выберите цвет дракона (черный/black, оранжевый/orange, белый/white, коричневый/brown): ", createColorMap(), "Неверно введен цвет!");
    }

    private static DragonType getType() {
        return getOptionalEnum("Выберите тип дракона (водный/water, подземный/underground, воздушный/air, огненный/fire)\nили нажмите Enter чтобы пропустить: ", createDragonTypeMap(), "Неверно введен тип!");
    }

    private static DragonCharacter getDragonCharacter() {
        return getRequiredEnum("Выберите характер дракона (хитрый/cunning, мудрый/wise, бешеный/chaotic,\nяростно-бешеный/chaotic_evil, непостоянный/fickle): ", createDragonCharacterMap(), "Неверно введен характер!");
    }

    private static Person getPerson() {
        while (true) {
            String choice = safeInput("Хотите ввести данные убийцы? (y/n): ").toLowerCase();
            if (choice.equals("n")) return null;
            if (choice.equals("y")) break;
        }
        String name = getNotEmptyString("Введите имя человека: ");
        String passportID = getOptionalPassportID();
        Location location = getRequiredLocation();
        return new Person(name, passportID, location);
    }

    private static String getOptionalPassportID() {
        while (true) {
            String input = safeInput("Введите паспортные данные (10-32 символа) или Enter чтобы пропустить: ");
            if (input.isEmpty()) return null;
            if (input.length() >= 10 && input.length() <= 32) return input;
            System.out.println("Длина паспортных данных должна быть от 10 до 32 символов!");
        }
    }

    private static Location getRequiredLocation() {
        System.out.println("\nВведите данные локации:");
        long x = getRequiredLong("Координата X: ");
        Double y = getRequiredDouble("Координата Y: ");
        long z = getRequiredLong("Координата Z: ");
        String name = getNotEmptyString("Название локации: ");
        return new Location(x, y, z, name);
    }

    private static long getRequiredLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(safeInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static Double getRequiredDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(safeInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static Map<String, Color> createColorMap() {
        Map<String, Color> map = new HashMap<>();
        map.put("черный", Color.BLACK); map.put("black", Color.BLACK);
        map.put("оранжевый", Color.ORANGE); map.put("orange", Color.ORANGE);
        map.put("белый", Color.WHITE); map.put("white", Color.WHITE);
        map.put("коричневый", Color.BROWN); map.put("brown", Color.BROWN);
        return map;
    }

    private static Map<String, DragonType> createDragonTypeMap() {
        Map<String, DragonType> map = new HashMap<>();
        map.put("водный", DragonType.WATER); map.put("water", DragonType.WATER);
        map.put("подземный", DragonType.UNDERGROUND); map.put("underground", DragonType.UNDERGROUND);
        map.put("воздушный", DragonType.AIR); map.put("air", DragonType.AIR);
        map.put("огненный", DragonType.FIRE); map.put("fire", DragonType.FIRE);
        return map;
    }

    private static Map<String, DragonCharacter> createDragonCharacterMap() {
        Map<String, DragonCharacter> map = new HashMap<>();
        map.put("хитрый", DragonCharacter.CUNNING); map.put("cunning", DragonCharacter.CUNNING);
        map.put("мудрый", DragonCharacter.WISE); map.put("wise", DragonCharacter.WISE);
        map.put("бешеный", DragonCharacter.CHAOTIC); map.put("chaotic", DragonCharacter.CHAOTIC);
        map.put("яростно-бешеный", DragonCharacter.CHAOTIC_EVIL); map.put("chaotic_evil", DragonCharacter.CHAOTIC_EVIL);
        map.put("непостоянный", DragonCharacter.FICKLE); map.put("fickle", DragonCharacter.FICKLE);
        return map;
    }

    private static <T> T getRequiredEnum(String prompt, Map<String, T> valueMap, String errorMessage) {
        while (true) {
            String input = safeInput(prompt).toLowerCase();
            T value = valueMap.get(input);
            if (value != null) return value;
            System.out.println(errorMessage);
        }
    }

    private static <T> T getOptionalEnum(String prompt, Map<String, T> valueMap, String errorMessage) {
        while (true) {
            String input = safeInput(prompt).toLowerCase();
            if (input.isEmpty()) return null;
            T value = valueMap.get(input);
            if (value != null) return value;
            System.out.println(errorMessage);
        }
    }
}
