package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import progclasses.Dragon;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Команда вывода всех элементов коллекции.
 * Отображает элементы коллекции, отсортированные по ID.
 */
public class ShowCommand implements ICommand, Serializable {
    /**
     * Выводит все элементы коллекции в порядке возрастания ID.
     * @param argument не используется
     * @param manager менеджер коллекции
     * @return CommandResponse с результатом выполнения
     */
    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        if (manager.getCollection().isEmpty()) {
            return new CommandResponse("Коллекция пуста", false);
        }

        String result = manager.getCollection().stream()
                .sorted(Comparator.comparingLong(Dragon::getId))
                .map(Dragon::toString)
                .collect(Collectors.joining("\n"));

        return new CommandResponse(result, true);
    }


    /** @return описание команды */
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String toString() {
        return "ShowCommand []";
    }
}
