package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import functions.CollectionManager;
import functions.CommandManager;
import progclasses.Color;
import progclasses.Dragon;

/**
 * Команда вывода значений цвета всех элементов коллекции в порядке убывания.
 * Сортирует и выводит только значения поля color элементов коллекции.
 */
public class PrintFDCCommand implements ICommand {
    /**
     * Выводит значения поля color всех элементов в порядке убывания.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд (не используется)
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        List<Color> colors = new ArrayList<>();
        for (Dragon d : collectionManager.getCollection()) {
            colors.add(d.getColor());
        }

        colors.sort(Comparator.comparing(Color::toString).reversed());

        for (Color c : colors) {
            System.out.println(c);
        }
    }

    /** @return имя команды */
    @Override
    public String getName() {
        return "print_field_descending_color";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "значения поля color всех элементов в порядке убывания";
    }
}