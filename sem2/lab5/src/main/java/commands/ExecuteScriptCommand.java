package commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import exception.BadArgumentException;
import functions.CollectionManager;
import functions.CommandManager;
import functions.GetUniqueId;
import progclasses.*;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Поддерживает обработку сложных команд и предотвращает рекурсивные вызовы.
 */
public class ExecuteScriptCommand implements ICommand {
    private static final Stack<String> scriptStack = new Stack<>();
    private static final ArrayList<String> complexCommands = new ArrayList<>();

    static {
        complexCommands.add("add");
        complexCommands.add("add_if_min");
        complexCommands.add("add_if_max");
        complexCommands.add("update_id");
    }

    /**
     * Выполняет скрипт из указанного файла.
     * @param args путь к файлу скрипта
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException при ошибках чтения файла
     * @throws BadArgumentException при неверных аргументах или рекурсии
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager)
            throws IOException, BadArgumentException {
        if (args.length == 0) {
            throw new BadArgumentException("Путь не обнаружен");
        }

        String path = args[0];

        if (scriptStack.contains(path)) {
            throw new BadArgumentException("Обнаружена рекурсия, выполнение невозможно, обновите файл.");
        }

        scriptStack.push(path);

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                System.out.println(">>> " + line);
                String[] parts = line.split(" ", 2);
                String command = parts[0];

                try {
                    if (complexCommands.contains(command)) {
                        processComplexCommand(reader, command, parts, collectionManager, commandManager);
                    } else {
                        commandManager.execute(line);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка в данных: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Ошибка при выполнении команды '" + command + "': " + e.getMessage());
                }
            }
        } finally {
            scriptStack.pop();
        }
    }

    /**
     * Обрабатывает сложные команды, требующие дополнительных данных.
     * @param reader для чтения дополнительных данных
     * @param command выполняемая команда
     * @param parts разобранные аргументы команды
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException при ошибках чтения
     * @throws BadArgumentException при неверных аргументах
     */
    private void processComplexCommand(BufferedReader reader, String command, String[] parts,
                                       CollectionManager collectionManager, CommandManager commandManager)
            throws IOException, BadArgumentException {
        switch (command) {
            case "add":
                Dragon dragon = readDragonFromScript(reader, GetUniqueId.generateId(collectionManager));
                collectionManager.addDragon(dragon);
                System.out.println("Новый дракон успешно добавлен!");
                break;
            case "add_if_min":
                Dragon dragon3 = readDragonFromScript(reader, GetUniqueId.generateId(collectionManager));
                Optional<Dragon> maxProduct = collectionManager.getCollection().stream().max(Dragon::compareTo);
                if (maxProduct.isEmpty() || maxProduct.get().compareTo(dragon3) < 0) {
                    collectionManager.addDragon(dragon3);
                    System.out.println("Максимальный дракон успешно добавлен");
                }
                break;
            case "add_if_max":
                Dragon dragon4 = readDragonFromScript(reader, GetUniqueId.generateId(collectionManager));
                Optional<Dragon> minProduct = collectionManager.getCollection().stream().min(Dragon::compareTo);
                if (minProduct.isEmpty() || minProduct.get().compareTo(dragon4) > 0) {
                    collectionManager.addDragon(dragon4);
                    System.out.println("Минимальный дракон успешно добавлен");
                }
                break;
            case "update_id":
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    System.out.println("Ошибка: ID не был передан.");
                    return;
                }

                long userId;
                try {
                    userId = Long.parseLong(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: ID должен быть числом. Получено: " + parts[1]);
                    return;
                }

                boolean removed = collectionManager.getCollection().removeIf(d -> Objects.equals(d.getId(), userId));
                if (!removed) {
                    System.out.println("Дракон с ID " + userId + " не найден в коллекции, но будет добавлен как новый.");
                }
                Dragon dragon_id = readDragonFromScript(reader, userId);
                collectionManager.addDragon(dragon_id);
                System.out.println("Дракон с ID " + userId + " успешно обновлён.");
                break;
        }
    }

    /**
     * Считывает данные дракона из скрипта.
     * @param reader для чтения данных
     * @param id ID дракона
     * @return созданный объект Dragon
     * @throws IOException при ошибках чтения
     * @throws IllegalArgumentException при неверных данных
     */
    private Dragon readDragonFromScript(BufferedReader reader, Long id) throws IOException, IllegalArgumentException {
        try {
            String name = readNonEmptyLine(reader, "Имя дракона не задано");
            if (name.isEmpty()) throw new IOException("Имя дракона не может быть пустым");

            // Чтение координат
            String[] coords = readNonEmptyLine(reader, "Координаты не заданы").split(" ");
            if (coords.length != 2) throw new IOException("Неверный формат координат (требуется x y)");
            long x = parseLongSafe(coords[0], "Координата x");
            long y = parseLongSafe(coords[1], "Координата y");
            if (x > 511) throw new IOException("Координата x должна быть ≤ 511");
            Coordinates coordinates = new Coordinates(x, y);

            // Чтение возраста
            long age = parseLongSafe(readNonEmptyLine(reader, "Возраст не задан"), "Возраст");
            if (age <= 0) throw new IOException("Возраст должен быть > 0");

            // Чтение enum-значений
            Color color = parseEnumSafe(
                    readNonEmptyLine(reader, "Цвет не задан"),
                    Color.class,
                    "Недопустимый цвет. Допустимые значения: " + Arrays.toString(Color.values())
            );

            DragonCharacter character = parseEnumSafe(
                    readNonEmptyLine(reader, "Характер не задан"),
                    DragonCharacter.class,
                    "Недопустимый характер. Допустимые значения: " + Arrays.toString(DragonCharacter.values())
            );

            // Обработка nullable полей
            String typeLine = readLine(reader);
            DragonType type = null;
            if (typeLine != null && !typeLine.trim().equalsIgnoreCase("null")) {
                type = parseEnumSafe(
                        typeLine,
                        DragonType.class,
                        "Недопустимый тип дракона. Допустимые значения: " + Arrays.toString(DragonType.values()) + " или null"
                );
            }

            // Чтение убийцы
            String killerName = readLine(reader);
            Person killer = null;
            if (killerName != null && !killerName.trim().equalsIgnoreCase("null")) {
                String passport = readLine(reader);

                String[] locParts = readNonEmptyLine(reader, "Локация не задана").split(" ");
                if (locParts.length < 4) throw new IOException("Неверный формат локации (x y z name)");

                Location location = new Location(
                        parseLongSafe(locParts[0], "Координата локации x"),
                        parseDoubleSafe(locParts[1]),
                        parseLongSafe(locParts[2], "Координата локации z"),
                        locParts[3]
                );

                killer = new Person(
                        killerName.trim(),
                        passport != null && passport.trim().isEmpty() ? null : Objects.requireNonNull(passport).trim(),
                        location
                );
            }

            Dragon dragon = new Dragon();
            dragon.setId(id);
            dragon.setName(name);
            dragon.setCoordinates(coordinates);
            dragon.setAge(age);
            dragon.setColor(color);
            dragon.setCharacter(character);
            dragon.setType(type);
            dragon.setKiller(killer);

            return dragon;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка в данных дракона: " + e.getMessage(), e);
        }
    }

    /**
     * Читает непустую строку из скрипта.
     * @param reader для чтения
     * @param errorMessage сообщение об ошибке если строка пуста
     * @return прочитанную строку
     * @throws IOException если достигнут конец файла
     */
    private String readNonEmptyLine(BufferedReader reader, String errorMessage) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) return line;
        }
        throw new IOException(errorMessage);
    }

    /**
     * Читает строку из скрипта (может быть пустой).
     * @param reader для чтения
     * @return прочитанную строку или null
     * @throws IOException при ошибках чтения
     */
    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        return line != null ? line.trim() : null;
    }

    /**
     * Безопасно парсит строку в long.
     * @param value строка для парсинга
     * @param fieldName название поля для сообщения об ошибке
     * @return распарсенное число
     * @throws IOException если парсинг невозможен
     */
    private long parseLongSafe(String value, String fieldName) throws IOException {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IOException(fieldName + " должна быть целым числом");
        }
    }

    /**
     * Безопасно парсит строку в double.
     * @param value строка для парсинга
     * @return распарсенное число
     * @throws IOException если парсинг невозможен
     */
    private double parseDoubleSafe(String value) throws IOException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IOException("Координата локации y должна быть числом");
        }
    }

    /**
     * Безопасно парсит строку в enum.
     * @param value строка для парсинга
     * @param enumClass класс enum
     * @param errorMessage сообщение об ошибке
     * @return значение enum
     * @throws IllegalArgumentException если значение недопустимо
     */
    private <T extends Enum<T>> T parseEnumSafe(String value, Class<T> enumClass, String errorMessage)
            throws IllegalArgumentException {
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "execute_script";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "исполнить скрипт из указанного файла";
    }
}