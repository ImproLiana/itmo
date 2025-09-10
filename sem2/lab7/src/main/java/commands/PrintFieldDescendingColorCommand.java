package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import progclasses.Color;
import progclasses.Dragon;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда вывода значений поля color всех элементов коллекции в порядке убывания.
 */
public class PrintFieldDescendingColorCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        List<Color> sortedColors = manager.getCollection().stream()
                .map(Dragon::getColor)
                .sorted(Comparator.comparing(Color::toString).reversed())
                .collect(Collectors.toList());

        if (sortedColors.isEmpty()) {
            return new CommandResponse("Коллекция пуста, выводить нечего.", false);
        }

        String result = sortedColors.stream()
                .map(Color::toString)
                .collect(Collectors.joining("\n"));

        return new CommandResponse("Цвета в порядке убывания:\n" + result, true);
    }

    @Override
    public String getDescription() {
        return "вывести значения поля color всех элементов в порядке убывания";
    }
}
