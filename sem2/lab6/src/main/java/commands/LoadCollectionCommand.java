package commands;
import functions.WorkWithFile;
import myCommands.CommandResponse;
import progclasses.Dragon;
import server.ServerCollectionManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;


public class LoadCollectionCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, ServerCollectionManager manager) {
        if (!(argument instanceof String path)) {
            return new CommandResponse("Неверный путь к файлу", false);
        }
        manager.setPath(path);

        try {
            HashSet<Dragon> dragons = WorkWithFile.readFromFile(path);

            if (dragons.isEmpty()) {
                return new CommandResponse("Файл пуст. Коллекция не загружена.", true);
            }

            manager.setCollection(dragons);
            return new CommandResponse("Коллекция успешно загружена из файла: " + path, true);

        } catch (IOException e) {
            return new CommandResponse("Ошибка чтения файла: " + e.getMessage(), false);
        } catch (Exception e) {
            return new CommandResponse("Ошибка обработки файла: " + e.getMessage(), false);
        }
    }

    @Override
    public String getDescription() {
        return "load_collection [path] : загрузить коллекцию с сервера из указанного файла";
    }
}
