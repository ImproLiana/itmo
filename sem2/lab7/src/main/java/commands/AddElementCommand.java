package commands;

import java.io.Serializable;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import progclasses.Dragon;

/**
 * Команда для добавления нового элемента (дракона) в коллекцию.
 * Создает новый объект Dragon, заполняет его поля и добавляет в коллекцию.
 */
public class AddElementCommand implements ICommand, Serializable{

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        Dragon dragon = (Dragon) argument;
        boolean success = manager.addToDB(dragon, username, null);
        if (success) {
            return new CommandResponse("Дракон успешно добавлен!", true);
        } else {
            return new CommandResponse("Ошибка при добавлении дракона в базу данных.", false);
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}