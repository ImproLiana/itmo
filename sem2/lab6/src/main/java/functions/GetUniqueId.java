package functions;

import java.util.concurrent.ThreadLocalRandom;

import server.ServerCollectionManager;

import java.io.Serializable;
import java.util.Set;

/**
 * Класс для генерации уникальных идентификаторов.
 * Обеспечивает создание ID, не пересекающегося с уже существующими в коллекции.
 */
public class GetUniqueId implements Serializable{

    /**
     * Генерирует уникальный идентификатор, который отсутствует в текущей коллекции.
     *
     * @param collectionManager менеджер коллекции, содержащий уже используемые идентификаторы
     * @return уникальный идентификатор типа long
     */
    public static long generateId(ServerCollectionManager serverCollectionManager) {
        Set<Long> existingIds = serverCollectionManager.getAllIds(); // получаем один раз
        long id;
        do {
            id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
        } while (existingIds.contains(id));
        return id;
    }
}
