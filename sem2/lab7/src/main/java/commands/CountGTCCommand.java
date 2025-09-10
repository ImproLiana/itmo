package commands;

import java.io.Serializable;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import progclasses.DragonCharacter;

/**
 * Команда подсчета драконов с характером длиннее заданного.
 */
public class CountGTCCommand implements ICommand, Serializable {

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        if (!(argument instanceof String user_character)) {
            return new CommandResponse("Ошибка: аргумент должен быть строкой (character)", false);
        }

        try {
            DragonCharacter userCharacter = DragonCharacter.set(user_character);
            long count = manager.getCollection().stream()
                    .filter(d -> d.getCharacter().compareLength(userCharacter) > 0)
                    .count();

            return new CommandResponse("Количество драконов с характером больше чем " + user_character + ": " + count, true);

        } catch (IllegalArgumentException e) {
            return new CommandResponse("Ошибка: " + e.getMessage(), false);
        }
    }

    @Override
    public String getDescription() {
        return "count_greater_than_character character : количество элементов, значение поля character которых больше заданного";
    }

    @Override
    public String toString() {
        return "CountGTCCommand []";
    }
}
