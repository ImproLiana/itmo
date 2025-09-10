package commands;

import java.io.IOException;
import java.util.Optional;
import functions.CollectionManager;
import functions.CommandManager;
import functions.FillObject;
import functions.GetUniqueId;
import progclasses.Dragon;

/**
 * Команда добавления элемента, если он меньше минимального в коллекции.
 * Создает и добавляет новый объект Dragon только если он меньше всех существующих.
 */
public class AddIfMinCommand implements ICommand {
    /**
     * Выполняет проверку и добавление элемента при выполнении условия.
     * @param args не используются
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException при ошибках ввода/вывода
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        Optional<Dragon> minProduct = collectionManager.getCollection().stream().min(Dragon::compareTo);
        Dragon dragon = new Dragon();
        FillObject.fill(dragon, GetUniqueId.generateId(collectionManager));

        if (minProduct.isEmpty() || minProduct.get().compareTo(dragon) == 1) {
            collectionManager.addDragon(dragon);
            System.out.println("Минимальный дракон успешно добавлен");
        } else {
            System.out.println("Не является минимальным дрконом, минимальный:");
            System.out.println(minProduct.get());
        }
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "add_if_min";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}