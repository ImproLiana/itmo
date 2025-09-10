package functions;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import progclasses.*;

/**
 * Класс для работы с файлом коллекции.
 * Предоставляет методы чтения и записи объектов {@link Dragon} в CSV-формате.
 */
public class WorkWithFile {
    private static Long LastId;

    /**
     * Преобразует строку CSV в объект {@link Dragon}.
     *
     * @param csvLine строка в формате CSV
     * @return объект {@link Dragon}, если парсинг успешен, иначе {@code null}
     */
    private static Dragon parseDragonFromCsv(String csvLine) {
        String[] parts = csvLine.split(","); // Разделяем строку по запятым
        if (parts.length == 15 || parts.length == 10) {
            Dragon dragon = new Dragon();
            try {
                dragon.setId(Long.parseLong(parts[0]));
                LastId = Long.parseLong(parts[0]);
                dragon.setName(parts[1]);
                dragon.setCoordinates(new Coordinates(Long.parseLong(parts[2]), Long.parseLong(parts[3])));
                dragon.setCreationDate(LocalDate.parse(parts[4]));
                dragon.setAge(Long.parseLong(parts[5]));
                dragon.setColor(Color.set(parts[6]));
                if (parts[7] == "none") {
                    dragon.setType(null);
                } else {
                    dragon.setType(DragonType.set(parts[7]));
                }
                dragon.setCharacter(DragonCharacter.set(parts[8]));
                if (parts.length == 15) {
                    dragon.setKiller(new Person(parts[9], parts[10], new Location(Long.parseLong(parts[11]),
                            Double.parseDouble(parts[12]), Long.parseLong(parts[13]), parts[14])));
                } else {
                    dragon.setKiller(null);
                }
                return dragon;
            } catch (Exception e) {
                // Проблемы при парсинге строки в Dragon — возвращаем null
            }
        }
        return null;
    }

    /**
     * Сохраняет коллекцию драконов в указанный файл.
     * Если содержимое файла и коллекции совпадает, файл не перезаписывается.
     *
     * @param dragons коллекция объектов {@link Dragon}
     * @param path путь к файлу для сохранения
     * @throws IOException если возникает ошибка при работе с файлом
     */
    public static void saveToFile(Set<Dragon> dragons, String path) throws IOException {
        if (readFromFile(path).toString().trim().equals(dragons.toString())) {
            System.out.println("Коллекция не изменилась.");
        } else {
            try (FileOutputStream fos = new FileOutputStream(path, false);
                 OutputStreamWriter outputStream = new OutputStreamWriter(fos, "UTF-8")) {
                for (Dragon dragon : dragons) {
                    outputStream.write(dragon.values() + "\n");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }
    }

    /**
     * Загружает коллекцию объектов {@link Dragon} из указанного файла.
     *
     * @param path путь к файлу с данными
     * @return коллекция объектов {@link Dragon}
     * @throws IOException если возникает ошибка при чтении файла
     */
    public static HashSet<Dragon> readFromFile(String path) throws IOException {
        HashSet<Dragon> dragons = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                Dragon dragon = parseDragonFromCsv(line);
                if (dragon != null) {
                    dragons.add(dragon);
                }
            }
        }
        return dragons;
    }
}
