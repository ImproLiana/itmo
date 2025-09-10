package commands;

import static functions.WorkWithFile.saveToFile;

import java.io.IOException;

import functions.CollectionManager;
import functions.CommandManager;

public class SaveCommand implements ICommand{
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException{
        saveToFile(collectionManager.getCollection(), "test.csv");
    }

    @Override
    public String getName(){
        return "save";
    }

    @Override
    public String getDescription(){
        return "сохранение коллекцию в файл";
    }
}
