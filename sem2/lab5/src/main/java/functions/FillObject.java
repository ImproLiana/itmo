package functions;

import java.time.LocalDate;
import java.util.*;
import progclasses.*;

/**
 * Класс для интерактивного заполнения объекта Dragon.
 * Обеспечивает пошаговый ввод всех полей дракона с валидацией данных.
 */
public class FillObject {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Заполняет объект Dragon данными, введенными пользователем.
     * @param dragon объект Dragon для заполнения
     * @param id уникальный идентификатор дракона
     */
    public static void fill(Dragon dragon, long id) {
        dragon.setId(id);
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

    /**
     * Получает непустую строку от пользователя.
     * @param prompt приглашение для ввода
     * @return введенная непустая строка
     */
    private static String getNotEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Значение не может быть пустым!");
        }
    }

    /**
     * Получает положительное целое число от пользователя.
     * @param prompt приглашение для ввода
     * @return введенное положительное число
     */
    private static long getLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                long value = Long.parseLong(scanner.nextLine());
                if (value > 0) {
                    return value;
                }
                System.out.println("Значение должно быть больше 0!");
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    /**
     * Получает координаты дракона от пользователя.
     * @return объект Coordinates с введенными значениями
     */
    private static Coordinates getCoordinates() {
        System.out.println("\nВведите координаты дракона:");
        long x = getX();
        long y = getY();
        return new Coordinates(x, y);
    }

    /**
     * Получает координату X (≤ 511) от пользователя.
     * @return значение координаты X
     */
    private static long getX() {
        while (true) {
            System.out.print("Координата X (макс. 511): ");
            try {
                long x = Long.parseLong(scanner.nextLine());
                if (x <= 511) {
                    return x;
                }
                System.out.println("Максимальное значение X: 511!");
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    /**
     * Получает координату Y от пользователя.
     * @return значение координаты Y
     */
    private static long getY() {
        while (true) {
            System.out.print("Координата Y: ");
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    /**
     * Получает цвет дракона от пользователя.
     * @return выбранный цвет
     */
    private static Color getColor() {
        Map<String, Color> colorMap = createColorMap();
        return getRequiredEnum(
                "Выберите цвет дракона (черный/black, оранжевый/orange, белый/white, коричневый/brown): ",
                colorMap,
                "Неверно введен цвет!"
        );
    }

    /**
     * Получает тип дракона от пользователя (может быть null).
     * @return выбранный тип или null
     */
    private static DragonType getType() {
        Map<String, DragonType> typeMap = createDragonTypeMap();
        return getOptionalEnum(
                "Выберите тип дракона (водный/water, подземный/underground, воздушный/air, огненный/fire)\n" +
                        "или нажмите Enter чтобы пропустить: ",
                typeMap,
                "Неверно введен тип!"
        );
    }

    /**
     * Получает характер дракона от пользователя.
     * @return выбранный характер
     */
    private static DragonCharacter getDragonCharacter() {
        Map<String, DragonCharacter> characterMap = createDragonCharacterMap();
        return getRequiredEnum(
                "Выберите характер дракона (хитрый/cunning, мудрый/wise, бешеный/chaotic,\n" +
                        "яростно-бешеный/chaotic_evil, непостоянный/fickle): ",
                characterMap,
                "Неверно введен характер!"
        );
    }

    /**
     * Получает данные об убийце дракона от пользователя.
     * @return объект Person или null, если убийца не указан
     */
    private static Person getPerson() {
        for (;;){
            System.out.print("Хотите ввести данные убийцы? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("n")) {
                return null;
            };
            if (choice.equals("y")){
                break;
            }
        }

        String name = getNotEmptyString("Введите имя человека: ");
        String passportID = getOptionalPassportID();
        Location location = getRequiredLocation();

        return new Person(name, passportID, location);
    }

    /**
     * Получает необязательные паспортные данные (10-32 символа).
     * @return паспортные данные или null
     */
    private static String getOptionalPassportID() {
        while (true) {
            System.out.print("Введите паспортные данные (10-32 символа) или Enter чтобы пропустить: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            if (input.length() >= 10 && input.length() <= 32) {
                return input;
            }

            System.out.println("Длина паспортных данных должна быть от 10 до 32 символов!");
        }
    }

    /**
     * Получает обязательные данные о местоположении.
     * @return объект Location
     */
    private static Location getRequiredLocation() {
        System.out.println("\nВведите данные локации:");
        long x = getRequiredLong("Координата X: ");
        Double y = getRequiredDouble("Координата Y: ");
        long z = getRequiredLong("Координата Z: ");
        String name = getNotEmptyString("Название локации: ");

        return new Location(x, y, z, name);
    }

    /**
     * Получает обязательное целое число от пользователя.
     * @param prompt приглашение для ввода
     * @return введенное число
     */
    private static long getRequiredLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    /**
     * Получает обязательное дробное число от пользователя.
     * @param prompt приглашение для ввода
     * @return введенное число
     */
    private static Double getRequiredDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    /**
     * Создает маппинг строковых значений на enum Color.
     * @return Map с вариантами цветов
     */
    private static Map<String, Color> createColorMap() {
        Map<String, Color> map = new HashMap<>();
        map.put("черный", Color.BLACK);
        map.put("black", Color.BLACK);
        map.put("оранжевый", Color.ORANGE);
        map.put("orange", Color.ORANGE);
        map.put("белый", Color.WHITE);
        map.put("white", Color.WHITE);
        map.put("коричневый", Color.BROWN);
        map.put("brown", Color.BROWN);
        return map;
    }

    /**
     * Создает маппинг строковых значений на enum DragonType.
     * @return Map с вариантами типов драконов
     */
    private static Map<String, DragonType> createDragonTypeMap() {
        Map<String, DragonType> map = new HashMap<>();
        map.put("водный", DragonType.WATER);
        map.put("water", DragonType.WATER);
        map.put("подземный", DragonType.UNDERGROUND);
        map.put("underground", DragonType.UNDERGROUND);
        map.put("воздушный", DragonType.AIR);
        map.put("air", DragonType.AIR);
        map.put("огненный", DragonType.FIRE);
        map.put("fire", DragonType.FIRE);
        return map;
    }

    /**
     * Создает маппинг строковых значений на enum DragonCharacter.
     * @return Map с вариантами характеров драконов
     */
    private static Map<String, DragonCharacter> createDragonCharacterMap() {
        Map<String, DragonCharacter> map = new HashMap<>();
        map.put("хитрый", DragonCharacter.CUNNING);
        map.put("cunning", DragonCharacter.CUNNING);
        map.put("мудрый", DragonCharacter.WISE);
        map.put("wise", DragonCharacter.WISE);
        map.put("бешеный", DragonCharacter.CHAOTIC);
        map.put("chaotic", DragonCharacter.CHAOTIC);
        map.put("яростно-бешеный", DragonCharacter.CHAOTIC_EVIL);
        map.put("chaotic_evil", DragonCharacter.CHAOTIC_EVIL);
        map.put("непостоянный", DragonCharacter.FICKLE);
        map.put("fickle", DragonCharacter.FICKLE);
        return map;
    }

    /**
     * Получает обязательное значение enum от пользователя.
     * @param <T> тип enum
     * @param prompt приглашение для ввода
     * @param valueMap маппинг строковых значений на enum
     * @param errorMessage сообщение об ошибке
     * @return выбранное значение enum
     */
    private static <T> T getRequiredEnum(String prompt, Map<String, T> valueMap, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            T value = valueMap.get(input);
            if (value != null) {
                return value;
            }

            System.out.println(errorMessage);
        }
    }

    /**
     * Получает необязательное значение enum от пользователя.
     * @param <T> тип enum
     * @param prompt приглашение для ввода
     * @param valueMap маппинг строковых значений на enum
     * @param errorMessage сообщение об ошибке
     * @return выбранное значение enum или null
     */
    private static <T> T getOptionalEnum(String prompt, Map<String, T> valueMap, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                return null;
            }

            T value = valueMap.get(input);
            if (value != null) {
                return value;
            }

            System.out.println(errorMessage);
        }
    }
}