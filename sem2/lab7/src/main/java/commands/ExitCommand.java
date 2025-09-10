package commands;

import java.io.Serializable;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;

/**
 * Команда завершения клиентской сессии (без остановки сервера).
 */
public class ExitCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        return new CommandResponse("До свидания!", true); // Просто ответ клиенту

    }

    @Override
    public String getDescription() {
        return "exit : завершить клиентскую программу с сохранением коллекции";

    }

    @Override
    public String toString() {
        return "ExitCommand []";
    }
}
