package commands;

import java.io.IOException;
import java.io.Serializable;

import functions.FillObject;
import functions.GetUniqueId;
import myCommands.CommandResponse;
import progclasses.Dragon;
import server.ServerCollectionManager;

/**
 * Команда для добавления нового элемента (дракона) в коллекцию.
 * Создает новый объект Dragon, заполняет его поля и добавляет в коллекцию.
 */
public class AddElementCommand implements ICommand, Serializable{

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager manager) {
        Dragon dragon = (Dragon) argument;
        dragon.setId(GetUniqueId.generateId(manager));
        manager.addDragon(dragon);
        return new CommandResponse("Дракон успешно добавлен!", true);
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}