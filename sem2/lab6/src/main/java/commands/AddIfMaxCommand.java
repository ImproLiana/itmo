package commands;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import functions.FillObject;
import functions.GetUniqueId;
import myCommands.CommandResponse;
import progclasses.Dragon;
import server.ServerCollectionManager;

/**
 * Команда для добавления элемента, если он больше максимального в коллекции.
 * Сравнивает новый элемент с текущим максимальным перед добавлением.
 */
public class AddIfMaxCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager collectionManager) {
        Optional<Dragon> maxProduct = collectionManager.getCollection().stream().max(Dragon::compareTo);
        Dragon dragon = (Dragon) argument;
        dragon.setId(GetUniqueId.generateId(collectionManager));
        if (maxProduct.isEmpty() || maxProduct.get().compareTo(dragon) == -1) {
            collectionManager.addDragon(dragon);
            return new CommandResponse("Максимальный дракон успешно добавлен", true);
        } else {
            return new CommandResponse("Не является максимальным дрконом, максимальный: \n"+maxProduct.get(), false);
        }
    }


    /** @return описание команды */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    /** @return строковое представление команды */
    @Override
    public String toString() {
        return "AddIfMaxCommand []";
    }
}