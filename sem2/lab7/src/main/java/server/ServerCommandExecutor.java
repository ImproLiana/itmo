package server;

import functions.DataBaseCollectionManager;
import myCommands.CommandRequest;
import myCommands.CommandResponse;
import commands.ICommand;
import myCommands.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class ServerCommandExecutor implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ServerCommandExecutor.class);
    private static final Map<String, ICommand> commandMap = ServerCommandManager.getCommands();

    public static CommandResponse execute(CommandRequest request, DataBaseCollectionManager collectionManager) {
        String commandName = request.getCommandName();
        Object argument = request.getArgument();
        String username = request.getUsername();
        String password = request.getPassword();

        logger.debug("Получен запрос от пользователя '{}'. Команда: '{}'", username, commandName);

        ICommand command = commandMap.get(commandName);
        if (command == null) {
            logger.warn("Неизвестная команда: {}", commandName);
            return new CommandResponse("Неизвестная команда: " + commandName, false);
        }

        if (!Objects.equals(commandName, "login") && !Objects.equals(commandName, "register")) {
            if (!UserManager.checkUsersPassword(username, password)) {
                logger.warn("Неверный пароль для пользователя '{}'", username);
                return new CommandResponse("Неверный логин или пароль", false);
            }
        } else {
            logger.info("Пользователь регистрируется или входит в аккаунт");
        }

        try {
            if (Objects.equals(commandName, "check_id_exists")){
                collectionManager.addToHistory(username, "update_by_id");
            } else {
                collectionManager.addToHistory(username, commandName);
            }
            logger.debug("Команда '{}' добавлена в историю", commandName);

            logger.info("Выполнение команды '{}' для пользователя '{}'", commandName, username);
            long startTime = System.currentTimeMillis();

            CommandResponse response = command.execute(argument, collectionManager, username);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            if (response == null) {
                logger.error("Команда '{}' вернула null-ответ!", commandName);
                return new CommandResponse("Ошибка: команда не вернула результат", false);
            }

            if (response.isSuccess()) {
                logger.info("Команда '{}' выполнена успешно за {} ms", commandName, duration);
            } else {
                logger.warn("Команда '{}' завершилась с ошибкой за {} ms: {}", commandName, duration, response.getResultMessage());
            }

            return response;
        } catch (Exception e) {
            logger.error("Ошибка при выполнении команды '{}': {}", commandName, e.getMessage(), e);
            return new CommandResponse("Ошибка при выполнении команды: " + e.getMessage(), false);
        }
    }
}
