package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import server.ServerCommandManager;

import java.io.Serializable;
import java.util.Map;

/**
 * Команда вывода справки по всем доступным командам.
 */
public class HelpCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        Map<String, ICommand> allCommands = ServerCommandManager.getCommands();
        StringBuilder helpText = new StringBuilder();

        for (Map.Entry<String, ICommand> entry : allCommands.entrySet()) {
            helpText.append(entry.getKey()).append(" — ").append(entry.getValue().getDescription()).append("\n");
        }

        return new CommandResponse(helpText.toString(), true);
    }

    @Override
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }

    @Override
    public String toString() {
        return "HelpCommand []";
    }
}
