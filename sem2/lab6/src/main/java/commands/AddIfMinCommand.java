package commands;

import java.io.Serializable;
import java.util.Optional;

import myCommands.CommandResponse;
import progclasses.Dragon;
import server.ServerCollectionManager;
import functions.GetUniqueId;

/**
 * Команда добавления элемента, если он меньше минимального в коллекции.
 */
public class AddIfMinCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager collectionManager) {
        if (!(argument instanceof Dragon dragon)) {
            return new CommandResponse("Ошибка: ожидается объект типа Dragon", false);
        }

        Optional<Dragon> minDragon = collectionManager.getCollection().stream().min(Dragon::compareTo);
        dragon.setId(GetUniqueId.generateId(collectionManager));

        if (minDragon.isEmpty() || dragon.compareTo(minDragon.get()) < 0) {
            collectionManager.addDragon(dragon);
            return new CommandResponse("Минимальный дракон успешно добавлен", true);
        } else {
            return new CommandResponse("Дракон НЕ добавлен: не минимальный.\nМинимальный сейчас: \n" + minDragon.get(), false);
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
