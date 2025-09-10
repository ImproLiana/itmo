package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import functions.CollectionManager;
import functions.CommandManager;
import progclasses.Dragon;

/**
 * Команда вывода элементов коллекции в порядке возрастания.
 * Сортирует и выводит всех драконов из коллекции.
 */
public class PrintAscendingCommand implements ICommand {
    /**
     * Сортирует и выводит элементы коллекции в порядке возрастания.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд (не используется)
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        List<Dragon> sortedDragons = new ArrayList<>(collectionManager.getCollection());
        Collections.sort(sortedDragons);

        for (Dragon d : sortedDragons) {
            System.out.println(d);
        }
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "print_ascending";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "элементы коллекции в порядке возрастания";
    }
}