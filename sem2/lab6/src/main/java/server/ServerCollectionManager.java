package server;

import static functions.WorkWithFile.*;

import functions.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import progclasses.Dragon;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ServerCollectionManager extends CollectionManager implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ServerCollectionManager.class);

    private HashSet<Dragon> collection = new HashSet<>();
    private String path;
    private final LinkedList<String> commandHistory = new LinkedList<>();

    public void setCollection(HashSet<Dragon> collection) {
        if (collection != null) {
            this.collection = collection;
        }
    }

    public void addDragon(Dragon dragon) {
        if (!collection.contains(dragon)) {
            collection.add(dragon);
            logger.debug("Дракон с ID {} добавлен в коллекцию", dragon.getId());
        } else {
            logger.debug("Дракон с ID {} уже существует, добавление отменено", dragon.getId());
        }
    }

    public void removeDragon(Dragon dragon) {
        if (collection.remove(dragon)) {
            logger.debug("Дракон с ID {} удалён", dragon.getId());
        } else {
            logger.debug("Дракон с ID {} не найден, удаление не выполнено", dragon.getId());
        }
    }

    public void clearCollection() {
        collection.clear();
        logger.info("Коллекция очищена");
    }

    public boolean updateById(Long id, Dragon newDragon) {
        boolean removed = collection.removeIf(dragon -> dragon.getId().equals(id));
        if (removed) {
            collection.add(newDragon);
            logger.info("Дракон с ID {} обновлён", id);
        } else {
            logger.warn("Обновление не выполнено: дракон с ID {} не найден", id);
        }
        return removed;
    }

    public HashSet<Dragon> getCollection() {
        return collection;
    }

    public void loadCollection(String path) {
        try {
            this.setCollection(readFromFile(path));
            logger.info("Коллекция успешно загружена из файла: {}", path);
        } catch (IOException e) {
            logger.error("Ошибка загрузки коллекции из файла {}: {}", path, e.getMessage());
        }
    }

    public Set<Long> getAllIds() {
        return collection.stream()
                .map(Dragon::getId)
                .collect(Collectors.toSet());
    }

    public boolean shouldAddIfMin(Dragon dragon) {
        return collection.isEmpty() || dragon.compareTo(getMinDragon()) < 0;
    }

    public boolean shouldAddIfMax(Dragon dragon) {
        return collection.isEmpty() || dragon.compareTo(getMaxDragon()) > 0;
    }

    public Dragon getMinDragon() {
        return collection.stream().min(Comparator.naturalOrder()).orElse(null);
    }

    public Dragon getMaxDragon() {
        return collection.stream().max(Comparator.naturalOrder()).orElse(null);
    }

    public void addToHistory(String command) {
        if (commandHistory.size() >= 12) {
            commandHistory.removeFirst();
        }
        commandHistory.add(command);
        logger.debug("Команда '{}' добавлена в историю", command);
    }

    public LinkedList<String> getCommandHistory() {
        return new LinkedList<>(commandHistory); // возвращаем копию
    }

    public void setPath(String path) {
        this.path = path;
        logger.info("Путь к файлу коллекции установлен: {}", path);
    }

    public String getPath() {
        return path;
    }
}
