package commands;

import java.io.IOException;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Команда вывода всех элементов коллекции.
 * Отображает элементы коллекции, отсортированные по ID.
 */
public class ShowCommand implements ICommand {
    /**
     * Выводит все элементы коллекции в порядке возрастания ID.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд (не используется)
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        collectionManager.getCollection().stream()
                .sorted((dragon1, dragon2) -> Long.compare(dragon1.getId(), dragon2.getId()))
                .forEach(System.out::println);
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "show";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "все элементы коллекции";
    }
}