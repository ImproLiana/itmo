package commands;

import java.io.IOException;
import java.util.Scanner;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Команда удаления элемента коллекции по его ID.
 * Поддерживает интерактивное подтверждение при некорректном вводе.
 */
public class RemoveByIdCommand implements ICommand {
    private static final Scanner scanner = new Scanner(System.in);
    private Long userId = null;
    private boolean find = false;

    /**
     * Удаляет элемент коллекции по указанному ID.
     * @param args аргументы команды (ожидается один числовой аргумент - ID)
     * @param collectionManager менеджер коллекции
     * @param commandManager менеджер команд (не используется)
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
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

        if (find && userId != null) {
            removeDragonById(collectionManager);
        }
    }

    /**
     * Обрабатывает случай, когда передано несколько аргументов.
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
     * Удаляет дракона по ID из коллекции.
     * @param collectionManager менеджер коллекции
     */
    private void removeDragonById(CollectionManager collectionManager) {
        boolean removed = collectionManager.getCollection().removeIf(dragon -> dragon.getId() == userId);
        if (removed) {
            System.out.println("Объект успешно удалён.");
        } else {
            System.out.println("Объект с таким ID не найден.");
        }
    }

    /** @return имя команды с форматом аргументов */
    @Override
    public String getName() {
        return "remove_by_id id";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "удаление элемента из коллекции по его id";
    }
}