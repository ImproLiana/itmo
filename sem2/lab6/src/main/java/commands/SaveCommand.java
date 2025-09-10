package commands;

import myCommands.CommandResponse;
import server.ServerCollectionManager;

import static functions.WorkWithFile.saveToFile;

import java.io.Serializable;

/**
 * Команда сохранения коллекции в файл.
 * Сохраняет текущую коллекцию в файл "test.csv".
 */
public class SaveCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager collectionManager) {
        String path = collectionManager.getPath(); // значение по умолчанию

        if (argument instanceof String argStr && !argStr.trim().isEmpty()) {
            path = argStr.trim();
        }

        try {
            saveToFile(collectionManager.getCollection(), path);
            return new CommandResponse("Коллекция успешно сохранена в файл: " + path, true);
        } catch (Exception e) {
            return new CommandResponse("Ошибка при сохранении в файл: " + e.getMessage(), false);
        }
    }


    @Override
    public String getDescription() {
        return "сохранение коллекции в файл";
    }
}
