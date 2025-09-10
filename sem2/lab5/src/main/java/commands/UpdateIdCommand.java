package commands;

import java.io.IOException;
import java.util.Scanner;
import functions.CollectionManager;
import functions.CommandManager;
import functions.FillObject;
import progclasses.Dragon;

/**
 * Команда обновления элемента коллекции по заданному ID.
 * Заменяет существующий элемент (или добавляет новый) с указанным ID.
 */
public class UpdateIdCommand implements ICommand {
    private static final Scanner scanner = new Scanner(System.in);
    private Long userId = null;
    private boolean find = false;

    /**
     * Обновляет элемент коллекции с указанным ID.
     * @param args аргументы команды (ожидается один числовой аргумент - ID)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд (не используется)
     * @throws IOException при ошибках ввода данных
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        processIdArgument(args);

        if (find && userId != null) {
            updateDragonInCollection(collectionManager);
        }
    }

    /**
     * Обрабатывает аргументы команды для получения ID.
     * @param args массив аргументов команды
     */
    private void processIdArgument(String[] args) {
        if (args.length == 1) {
            try {
                userId = Long.parseLong(args[0]);
                find = true;
            } catch (NumberFormatException e) {
                System.out.println("Неверное значение ID, введи число.");
            }
        } else if (args.length > 1) {
            handleMultipleArguments(args);
        } else {
            System.out.println("Ты не ввёл значение ID.");
        }
    }

    /**
     * Обрабатывает случай множественных аргументов.
     * @param args массив аргументов команды
     */
    private void handleMultipleArguments(String[] args) {
        while (true) {
            System.out.print("Было получено несколько значений вместо одного. Продолжить только с первым числом? (y/n): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("y")) {
                try {
                    userId = Long.parseLong(args[0]);
                    find = true;
                } catch (NumberFormatException e) {
                    System.out.println("Неверное значение ID, введи число.");
                }
                break;
            } else if (input.equalsIgnoreCase("n")) {
                find = false;
                break;
            } else {
                System.out.println("Пожалуйста, введи 'y' или 'n'.");
            }
        }
    }

    /**
     * Обновляет дракона в коллекции по ID.
     * @param collectionManager менеджер коллекции
     * @throws IOException при ошибках ввода данных
     */
    private void updateDragonInCollection(CollectionManager collectionManager) throws IOException {
        collectionManager.getCollection().removeIf(dragon -> dragon.getId() == userId);
        Dragon dragon = new Dragon();
        FillObject.fill(dragon, userId);
        collectionManager.addDragon(dragon);
        System.out.println("Элемент с ID " + userId + " успешно обновлён.");
    }

    /** @return имя команды с указанием аргумента */
    @Override
    public String getName() {
        return "update id";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "обновление значения элемента коллекции, id которого равен заданному";
    }
}