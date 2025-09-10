package commands;

import java.io.IOException;
import functions.CollectionManager;
import functions.CommandManager;
import progclasses.Dragon;
import progclasses.DragonCharacter;

/**
 * Команда подсчета драконов с характером длиннее заданного.
 */
public class CountGTCCommand implements ICommand {
    private String user_character;

    /**
     * Выполняет подсчет драконов с характером длиннее заданного.
     * @param args аргумент команды (характер для сравнения)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд
     * @throws IOException при ошибках ввода/вывода
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        if (args.length == 1) {
            user_character = args[0];
            try {
                DragonCharacter userCharacter = DragonCharacter.set(user_character);
                int counter = 0;
                for (Dragon d : collectionManager.getCollection()) {
                    if (d.getCharacter().compareLength(userCharacter) > 0) {
                        counter++;
                    }
                }
                System.out.println("Количество драконов с характером длиннее, чем " + user_character + ": " + counter);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Неверно введены аргументы");
        }
    }

    /** @return имя команды с форматом аргументов */
    @Override
    public String getName() {
        return "count_greater_than_character character";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "количество элементов, значение поля character которых больше заданного";
    }
}