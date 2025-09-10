package functions;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Set;

/**
 * Класс для генерации уникальных идентификаторов.
 * Обеспечивает создание ID, не пересекающегося с уже существующими в коллекции.
 */
public class GetUniqueId {

    /**
     * Генерирует уникальный идентификатор, который отсутствует в текущей коллекции.
     *
     * @param collectionManager менеджер коллекции, содержащий уже используемые идентификаторы
     * @return уникальный идентификатор типа long
     */
    public static long generateId(CollectionManager collectionManager) {
        Set<Long> existingIds = collectionManager.getAllIds(); // получаем один раз
        long id;
        do {
            id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
        } while (existingIds.contains(id));
        return id;
    }
}
