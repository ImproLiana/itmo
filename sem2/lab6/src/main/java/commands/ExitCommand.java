package commands;

import java.io.Serializable;

import myCommands.CommandResponse;
import server.ServerCollectionManager;

/**
 * Команда завершения клиентской сессии (без остановки сервера).
 */
public class ExitCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager manager) {
        new SaveCommand().execute(manager.getPath(), manager);
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
