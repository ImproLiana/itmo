package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import progclasses.Dragon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда вывода элементов коллекции в порядке возрастания.
 * Сортирует и возвращает всех драконов из коллекции.
 */
public class PrintAscendingCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        List<Dragon> sortedDragons = new ArrayList<>(manager.getCollection());
        Collections.sort(sortedDragons);

        if (sortedDragons.isEmpty()) {
            return new CommandResponse("Коллекция пуста.", false);
        }

        String result = sortedDragons.stream()
                .map(Dragon::toString)
                .collect(Collectors.joining("\n"));

        return new CommandResponse("Элементы в порядке возрастания:\n" + result, true);
    }

    @Override
    public String getDescription() {
        return "вывести элементы коллекции в порядке возрастания";
    }
}
