package server;

import commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerCommandManager implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ServerCommandManager.class);
    private static final Map<String, ICommand> commands = new HashMap<>();

    static {
        commands.put("add", new AddElementCommand());
        commands.put("add_if_min", new AddIfMinCommand());
        commands.put("add_if_max", new AddIfMaxCommand());
        commands.put("clear", new ClearCommand());
        commands.put("count_greater_than_character", new CountGTCCommand());
        commands.put("help", new HelpCommand());
        commands.put("history", new HistoryCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("info", new InfoCommand());
        commands.put("print_ascending", new PrintAscendingCommand());
        commands.put("print_field_descending_color", new PrintFieldDescendingColorCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("show", new ShowCommand());
        commands.put("update_id", new UpdateIdCommand());
        commands.put("exit", new ExitCommand());
        commands.put("load_collection", new LoadCollectionCommand());
        commands.put("check_id_exists", new CheckIdExistsCommand());
    }

    public static Map<String, ICommand> getCommands() {
        return commands;
    }

    public void startConsole(ServerCollectionManager collectionManager) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.print("[server-console] > ");
                    String input = scanner.nextLine().trim();

                    switch (input) {
                        case "save":
                            new SaveCommand().execute(collectionManager.getPath(), collectionManager);
                            if (collectionManager.getPath() == null) {
                                logger.warn("Коллекцию невозможно сохранить");
                            }
                            logger.info("Коллекция сохранена вручную через консоль.");
                            break;
                        case "exit":
                            new SaveCommand().execute(collectionManager.getPath(), collectionManager);
                            logger.info("Коллекция сохранена перед завершением работы сервера.");
                            logger.info("Завершение работы сервера.");
                            System.exit(0);
                            break;
                        default:
                            logger.warn("Неизвестная консольная команда: '{}'. Доступные команды: save, exit", input);
                    }
                } catch (NoSuchElementException | IllegalStateException e) {
                    logger.warn("Консольный ввод был прерван (например, Ctrl+D). Завершаем сервер.");
                    new SaveCommand().execute(collectionManager.getPath(), collectionManager);
                    System.exit(0);
                }
            }
        }).start();
    }
}
