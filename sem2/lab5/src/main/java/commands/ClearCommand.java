package commands;

import java.io.IOException;

import functions.CollectionManager;
import functions.CommandManager;
/**
 * Команда чистки коллекции.
 * Удаляет все записи в коллекции.
 */
public class ClearCommand implements ICommand{
    /**
     * Выполняет чистку коллекции
     * @param args не используются
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException при ошибках ввода/вывода
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException{
        collectionManager.clearCollection();
    }

    @Override
    public String getName(){
        return "clear";
    }

    @Override
    public String getDescription(){
        return "очистить коллекцию";
    }
}
