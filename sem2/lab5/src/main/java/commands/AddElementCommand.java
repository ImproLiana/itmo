package commands;

import java.io.IOException;
import functions.CollectionManager;
import functions.CommandManager;
import functions.FillObject;
import functions.GetUniqueId;
import progclasses.Dragon;

/**
 * Команда для добавления нового элемента (дракона) в коллекцию.
 * Создает новый объект Dragon, заполняет его поля и добавляет в коллекцию.
 */
public class AddElementCommand implements ICommand {
    /**
     * Выполняет команду добавления элемента.
     * @param args не используются
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException если возникли ошибки ввода/вывода
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        Dragon dragon = new Dragon();
        FillObject.fill(dragon, GetUniqueId.generateId(collectionManager));
        collectionManager.addDragon(dragon);
        System.out.println("Новый дракон успешно добавлен!");
    }

    /**
     * @return имя команды ("add")
     */
    @Override
    public String getName() {
        return "add";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}