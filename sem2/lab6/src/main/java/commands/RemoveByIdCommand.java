package commands;

import java.io.Serializable;

import myCommands.CommandResponse;
import server.ServerCollectionManager;

/**
 * Команда удаления элемента коллекции по его ID.
 */
public class RemoveByIdCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager collectionManager) {
        if (argument == null) {
            return new CommandResponse("Вы не указали ID для удаления.", false);
        }

        Long userId;

        try {
            if (argument instanceof String s) {
                userId = Long.parseLong(s.trim());
            } else if (argument instanceof Long l) {
                userId = l;
            } else {
                return new CommandResponse("Аргумент должен быть числом типа Long или строкой, содержащей число.", false);
            }
        } catch (NumberFormatException e) {
            return new CommandResponse("Невозможно распарсить ID: " + e.getMessage(), false);
        }

        boolean removed = collectionManager.getCollection().removeIf(dragon -> userId.equals(dragon.getId()));

        if (removed) {
            return new CommandResponse("Объект с ID " + userId + " успешно удалён.", true);
        } else {
            return new CommandResponse("Объект с ID " + userId + " не найден.", false);
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его ID";
    }
}
