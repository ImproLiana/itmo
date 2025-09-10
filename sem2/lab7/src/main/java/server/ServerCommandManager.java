package server;

import commands.*;
import functions.DataBaseCollectionManager;
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
//        commands.put("load_collection", new LoadCollectionCommand());
        commands.put("check_id_exists", new CheckIdExistsCommand());
        commands.put("login", new LoginUser());
        commands.put("register", new RegisterUser());
    }

    public static Map<String, ICommand> getCommands() {
        return commands;
    }

    public void startConsole(DataBaseCollectionManager collectionManager) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.print("[server-console] > ");
                    String input = scanner.nextLine().trim();

                    if (input.equals("exit")) {
                        logger.info("Завершение работы сервера.");
                        System.exit(0);
                    } else {
                        logger.warn("Неизвестная консольная команда: '{}'. Доступные команды: exit", input);
                    }
                } catch (NoSuchElementException | IllegalStateException e) {
                    logger.warn("Консольный ввод был прерван (например, Ctrl+D). Завершаем сервер.");
                    System.exit(0);
                }
            }
        }).start();
    }
}
