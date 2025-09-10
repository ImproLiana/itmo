package commands;

import java.io.Serializable;
import java.util.Optional;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import myCommands.DBconnector;
import progclasses.Dragon;

/**
 * Команда для добавления элемента, если он больше максимального в коллекции.
 * Сравнивает новый элемент с текущим максимальным перед добавлением.
 */
public class AddIfMaxCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager collectionManager, String username) {
        if (!(argument instanceof Dragon dragon)) {
            return new CommandResponse("Ошибка: ожидается объект типа Dragon", false);
        }
        if (collectionManager.shouldAddIfMax(dragon)){
            collectionManager.addToDB(dragon, username, null);
            return new CommandResponse("Максимальный дракон успешно добавлен", true);
        } else{
            return new CommandResponse("Дракон не максимальный", true);
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