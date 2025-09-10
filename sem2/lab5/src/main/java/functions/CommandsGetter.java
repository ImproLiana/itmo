package functions;

import java.util.Scanner;

/**
 * Класс для получения и обработки пользовательских команд.
 * Обеспечивает интерактивный ввод команд и их передачу на выполнение.
 */
public class CommandsGetter {
    private final CommandManager commandManager;

    /**
     * Создает обработчик команд с указанным путем к файлу коллекции.
     * @param path путь к файлу с коллекцией
     * @throws Exception при ошибках инициализации менеджера команд
     */
    public CommandsGetter(String path) throws Exception {
        this.commandManager = new CommandManager();
        commandManager.start(path);
    }

    /**
     * Запускает интерактивный цикл ввода команд.
     * Читает команды из стандартного ввода и передает их на выполнение.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать! Пожалуйста введите команду");

        while (true) {
            System.out.print(">>> ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                commandManager.execute(input);
            }
        }
    }
}