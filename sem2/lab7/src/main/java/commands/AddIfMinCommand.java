package commands;

import java.io.Serializable;
import java.util.Optional;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import progclasses.Dragon;

/**
 * Команда добавления элемента, если он меньше минимального в коллекции.
 */
public class AddIfMinCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager collectionManager, String username) {
        if (!(argument instanceof Dragon dragon)) {
            return new CommandResponse("Ошибка: ожидается объект типа Dragon", false);
        }

        if (collectionManager.shouldAddIfMin(dragon)) {
            collectionManager.addToDB(dragon, username, null);
            return new CommandResponse("Минимальный дракон успешно добавлен", true);
        } else {
            return new CommandResponse("Дракон НЕ добавлен: не минимальный.", false);
        }
    }

    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент, если он меньше минимального в коллекции";
    }

    @Override
    public String toString() {
        return "AddIfMinCommand []";
    }
}
