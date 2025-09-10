package commands;

import java.io.Serializable;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import myCommands.UpdatePayload;
import progclasses.Dragon;

/**
 * Команда обновления элемента коллекции по заданному ID.
 * Заменяет существующий элемент с указанным ID на переданный.
 */
public class UpdateIdCommand implements ICommand, Serializable {
    /**
     * Выполняет обновление элемента коллекции по ID.
     * @param argument массив из двух элементов: [0] — Long ID, [1] — Dragon
     * @param manager менеджер коллекции
     * @return результат выполнения команды
     */
    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        if (!(argument instanceof UpdatePayload payload)) {
            return new CommandResponse("Неверные аргументы. Ожидался UpdatePayload.", false);
        }

        Long userId = payload.getId();
        Dragon newDragon = payload.getDragon();
        boolean belongs = manager.checkBelonging(userId, username);
        if (belongs) {
            manager.updateById(userId, newDragon, username);
            return new CommandResponse("Элемент с ID " + userId + " успешно обновлён.", true);
        } else {
            return new CommandResponse("Элемент с ID " + userId + " принадлежит другому пользователю.", true);
        }
    }



    @Override
    public String getDescription() {
        return "обновление элемента по заданному ID";
    }

    @Override
    public String toString() {
        return "UpdateIdCommand []";
    }
}
