package commands;

import java.io.Serializable;
import java.sql.SQLException;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;

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
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) throws SQLException {
        boolean success = manager.clearCollection(username);

        if (success) {
            return new CommandResponse("Ваша коллекция успешно очищена.", true);
        } else {
            return new CommandResponse("У вас нет ни одного дракона для удаления.", false);
        }
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
