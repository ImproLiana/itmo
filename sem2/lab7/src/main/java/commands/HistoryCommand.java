package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Команда вывода истории последних команд.
 */
public class HistoryCommand implements ICommand, Serializable {
    /**
     * Выполняет вывод истории последних команд.
     * @param argument не используется
     * @param manager менеджер коллекции, в котором должна быть история
     * @return ответ с историей команд
     */
    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        LinkedList<String> history = manager.getCommandHistory(username);

        if (history.isEmpty()) {
            return new CommandResponse("История пуста.", true);
        }

        StringBuilder result = new StringBuilder("История команд:\n");
        for (String cmd : history) {
            result.append(cmd).append("\n");
        }

        return new CommandResponse(result.toString(), true);
    }

    @Override
    public String getDescription() {
        return "вывести последние 12 команд (без их аргументов)";
    }
}
