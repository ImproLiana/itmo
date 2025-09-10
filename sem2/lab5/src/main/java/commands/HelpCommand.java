package commands;

import java.io.IOException;
import java.util.HashMap;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Команда вывода справки по всем доступным командам.
 * Отображает список команд с их описаниями.
 */
public class HelpCommand implements ICommand {
    /**
     * Выводит список всех доступных команд с описаниями.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции (не используется)
     * @param commandManager менеджер команд, содержащий список команд
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        HashMap<String, ICommand> allCommands = commandManager.getCommandHashMap();
        for (ICommand command : allCommands.values()) {
            System.out.println(command.getName() + " -- " + command.getDescription());
        }
    }

    /** @return имя команды "help" */
    @Override
    public String getName() {
        return "help";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "справка по доступным командам";
    }
}