package commands;

import java.io.Serializable;

import myCommands.CommandResponse;
import server.ServerCollectionManager;

/**
 * Команда вывода информации о коллекции.
 * Отображает тип коллекции и количество элементов.
 */
public class InfoCommand implements ICommand, Serializable {

    /**
     * Выполняет вывод информации о коллекции.
     * @param argument не используется
     * @param manager менеджер коллекции
     * @return ответ с информацией о коллекции
     */
    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager manager) {
        String type = manager.getCollection().getClass().getName();
        int size = manager.getCollection().size();
        String message = "Тип коллекции: " + type + "\nРазмер коллекции: " + size;
        return new CommandResponse(message, true);
    }

    @Override
    public String getDescription() {
        return "информация о коллекции (тип, размер)";
    }
}
