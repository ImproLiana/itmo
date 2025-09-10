package functions;

import static functions.WorkWithFile.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import progclasses.Dragon;

/**
 * Менеджер для работы с коллекцией драконов.
 * Обеспечивает основные операции: добавление, удаление, загрузку и очистку коллекции.
 */
public class CollectionManager {
    private HashSet<Dragon> collection = new HashSet<>();

    /**
     * Устанавливает новую коллекцию драконов.
     * @param collection новая коллекция (если null, коллекция не изменяется)
     */
    public void setCollection(HashSet<Dragon> collection) {
        if (collection != null) {
            this.collection = collection;
        }
    }

    /**
     * Добавляет дракона в коллекцию, если его еще нет в ней.
     * @param dragon дракон для добавления
     */
    public void addDragon(Dragon dragon) {
        if (!collection.contains(dragon)) {
            collection.add(dragon);
        }
    }

    /**
     * Удаляет дракона из коллекции.
     * @param dragon дракон для удаления
     */
    public void removeDragon(Dragon dragon) {
        collection.remove(dragon);
    }

    /**
     * Очищает коллекцию драконов.
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Возвращает текущую коллекцию драконов.
     * @return коллекция драконов
     */
    public HashSet<Dragon> getCollection() {
        return collection;
    }

    /**
     * Загружает коллекцию из файла.
     * @param path путь к файлу с коллекцией
     */
    public void loadCollection(String path) {
        try {
            this.setCollection(readFromFile(path));
            System.out.println("Коллекция успешно загружена");
        } catch (IOException e) {
            System.out.println("Ошибка загрузки коллекции: " + e.getMessage());
        }
    }

    /**
     * Возвращает множество всех ID драконов в коллекции.
     * @return множество ID
     */
    public Set<Long> getAllIds() {
        return collection.stream()
                .map(Dragon::getId)
                .collect(Collectors.toSet());
    }
}