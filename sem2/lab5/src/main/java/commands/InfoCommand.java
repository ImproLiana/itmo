package commands;

import java.io.IOException;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Команда вывода информации о коллекции.
 * Отображает тип коллекции и количество элементов.
 */
public class InfoCommand implements ICommand {
    /**
     * Выводит основную информацию о коллекции.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд (не используется)
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        System.out.println("Тип коллекции: " + collectionManager.getCollection().getClass());
        System.out.println("Размер коллекции: " + collectionManager.getCollection().size());
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "info";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "информация о коллекции";
    }
}