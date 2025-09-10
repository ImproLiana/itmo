package commands;

import java.io.IOException;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Команда вывода истории выполненных команд.
 * Отображает последние 12 выполненных команд.
 */
public class HistoryCommand implements ICommand {
    /**
     * Выводит историю выполненных команд.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции (не используется)
     * @param commandManager менеджер команд
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        for (String command : commandManager.getCommandHistory()) {
            System.out.println(command);
        }
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "history";
    }

    /** @return описание команды  */
    @Override
    public String getDescription() {
        return "последние 12 команд";
    }
}