package commands;

import java.io.Serializable;

import myCommands.CommandResponse;
import myCommands.UpdatePayload;
import progclasses.Dragon;
import server.ServerCollectionManager;

/**
 * Команда обновления элемента коллекции по заданному ID.
 * Заменяет существующий элемент с указанным ID на переданный.
 */
public class UpdateIdCommand implements ICommand, Serializable {
    /**
     * Выполняет обновление элемента коллекции по ID.
     * @param argument массив из двух элементов: [0] — Long ID, [1] — Dragon
     * @param collectionManager менеджер коллекции
     * @return результат выполнения команды
     */
    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager collectionManager) {
        if (!(argument instanceof UpdatePayload payload)) {
            return new CommandResponse("Неверные аргументы. Ожидался UpdatePayload.", false);
        }

        Long userId = payload.getId();
        Dragon newDragon = payload.getDragon();

        boolean removed = collectionManager.getCollection().removeIf(dragon -> dragon.getId().equals(userId));

        if (!removed) {
            return new CommandResponse("Элемент с ID " + userId + " не найден.", false);
        }

        newDragon.setId(userId); // сохраняем ID
        collectionManager.addDragon(newDragon);

        return new CommandResponse("Элемент с ID " + userId + " успешно обновлён.", true);
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
