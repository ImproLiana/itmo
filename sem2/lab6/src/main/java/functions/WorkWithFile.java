package functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import progclasses.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Long.parseLong;

/**
 * Класс для работы с файлом коллекции.
 * Предоставляет методы чтения и записи объектов {@link Dragon} в CSV-формате.
 */
public class WorkWithFile {
    private static final Logger logger = LoggerFactory.getLogger(WorkWithFile.class);
    private static final Set<Long> ids = new HashSet<>();
    private static Long LastId;

    private static Dragon parseDragonFromCsv(String csvLine) {
        String[] parts = csvLine.split(",");

        if (parts.length != 15 && parts.length != 10) {
            logger.warn("Неверное количество полей в строке: {}", csvLine);
            return null;
        }

        try {
            Dragon dragon = new Dragon();

            long id = parseLong(parts[0]);
            while (ids.contains(id)) id++; // автоисправление дубликатов
            dragon.setId(id);
            ids.add(id);
            LastId = id;

            dragon.setName(parts[1]);
            dragon.setCoordinates(new Coordinates(parseLong(parts[2]), parseLong(parts[3])));
            dragon.setCreationDate(LocalDate.parse(parts[4]));
            dragon.setAge(parseLong(parts[5]));
            dragon.setColor(Color.set(parts[6]));

            dragon.setType("none".equals(parts[7]) ? null : DragonType.set(parts[7]));
            dragon.setCharacter(DragonCharacter.set(parts[8]));

            if (parts.length == 15) {
                dragon.setKiller(new Person(
                        parts[9],
                        parts[10],
                        new Location(
                                parseLong(parts[11]),
                                Double.parseDouble(parts[12]),
                                parseLong(parts[13]),
                                parts[14]
                        )
                ));
            } else {
                dragon.setKiller(null);
            }

            return dragon;
        } catch (Exception e) {
            logger.error("Ошибка при парсинге строки: {}\nПричина: {}", csvLine, e.toString());
            return null;
        }
    }

    public static HashSet<Dragon> readFromFile(String path) throws IOException {
        ids.clear();
        HashSet<Dragon> dragons = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), "UTF-8"))) {

            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                Dragon dragon = parseDragonFromCsv(line);
                if (dragon != null) {
                    dragons.add(dragon);
                } else {
                    logger.warn("Пропущена строка №{} из-за ошибки разбора", lineCount);
                }
            }
            logger.info("Загружено {} объектов из файла {}", dragons.size(), path);
        }

        return dragons;
    }

    public static void saveToFile(Set<Dragon> dragons, String path) throws IOException {
        try {
            if (readFromFile(path).equals(dragons)) {
                logger.info("Коллекция не изменилась. Файл не перезаписан.");
                return;
            }
        } catch (IOException e) {
            logger.warn("Файл {} не найден или не читается. Будет создан заново.", path);
        }

        try (OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(path, false), "UTF-8")) {
            for (Dragon dragon : dragons) {
                outputStream.write(dragon.values() + "\n");
            }
            logger.info("Коллекция сохранена в файл: {}", path);
        } catch (IOException e) {
            logger.error("Ошибка при записи в файл {}: {}", path, e.getMessage());
        }
    }
}
