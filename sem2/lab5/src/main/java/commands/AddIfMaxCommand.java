package commands;

import java.io.IOException;
import java.util.Optional;
import functions.CollectionManager;
import functions.CommandManager;
import functions.FillObject;
import functions.GetUniqueId;
import progclasses.Dragon;

/**
 * Команда для добавления элемента, если он больше максимального в коллекции.
 * Сравнивает новый элемент с текущим максимальным перед добавлением.
 */
public class AddIfMaxCommand implements ICommand {
    /**
     * Добавляет дракона, если он больше всех существующих в коллекции.
     * @param args не используются
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException при ошибках ввода/вывода
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        Optional<Dragon> maxProduct = collectionManager.getCollection().stream().max(Dragon::compareTo);
        Dragon dragon = new Dragon();
        FillObject.fill(dragon, GetUniqueId.generateId(collectionManager));

        if (maxProduct.isEmpty() || maxProduct.get().compareTo(dragon) == -1) {
            collectionManager.addDragon(dragon);
            System.out.println("Максимальный дракон успешно добавлен");
        } else {
            System.out.println("Не является максимальным дрконом, максимальный:");
            System.out.println(maxProduct.get());
        }
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "add_if_max";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    /** @return строковое представление команды */
    @Override
    public String toString() {
        return "AddIfMaxCommand []";
    }
}