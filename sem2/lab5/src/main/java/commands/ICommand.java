package commands;

import java.io.IOException;
import exception.BadArgumentException;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Базовый интерфейс для всех команд приложения.
 * Определяет обязательные методы, которые должны реализовывать все команды.
 */
public interface ICommand {
    /**
     * Основной метод выполнения команды.
     * @param args аргументы команды
     * @param collectionManager менеджер для работы с коллекцией
     * @param commandManager менеджер для доступа к другим командам
     * @throws IOException при ошибках ввода/вывода
     * @throws BadArgumentException при неверных аргументах команды
     */
    void run(String[] args, CollectionManager collectionManager, CommandManager commandManager)
            throws IOException, BadArgumentException;

    /**
     * Возвращает имя команды.
     * @return уникальное имя команды
     */
    String getName();

    /**
     * Возвращает описание команды.
     * @return краткое описание функциональности команды
     */
    String getDescription();
}