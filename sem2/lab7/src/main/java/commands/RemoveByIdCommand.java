package commands;

import java.io.Serializable;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;

/**
 * Команда удаления элемента коллекции по его ID.
 */
public class RemoveByIdCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
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
        boolean belongs = manager.checkBelonging(userId, username);
        boolean removed;
        if (belongs) {
            removed = manager.removeFromDB(userId);
            if (removed) {
                return new CommandResponse("Объект с ID " + userId + " успешно удалён.", true);
            } else {
                return new CommandResponse("Объект с ID " + userId + " не найден.", false);
            }
        } else {
            return new CommandResponse("Объект с ID " + userId + " принадлежит другому пользователю.", false);
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его ID";
    }
}
