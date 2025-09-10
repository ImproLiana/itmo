package commands;

import java.io.Serializable;

import myCommands.CommandResponse;
import server.ServerCollectionManager;

/**
 * Команда очистки коллекции.
 * Удаляет все записи в коллекции.
 */
public class ClearCommand implements ICommand, Serializable {

    /**
     * Выполняет очистку коллекции.
     * @param argument не используется (ожидается null)
     * @param manager менеджер коллекции на сервере
     * @return результат выполнения
     */
    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager manager) {
        manager.clearCollection();
        return new CommandResponse("Коллекция успешно очищена.", true);
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }

    @Override
    public String toString() {
        return "ClearCommand []";
    }
}
