package server;

import myCommands.CommandRequest;
import myCommands.CommandResponse;
import commands.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

public class ServerCommandExecutor implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ServerCommandExecutor.class);
    private static final Map<String, ICommand> commandMap = ServerCommandManager.getCommands();

    public static CommandResponse execute(CommandRequest request, ServerCollectionManager serverCollectionManager) {
        String commandName = request.getCommandName();
        Object argument = request.getArgument();

        ICommand command = commandMap.get(commandName);

        if (command == null) {
            logger.warn("Неизвестная команда: {}", commandName);
            return new CommandResponse("Неизвестная команда: " + commandName, false);
        }

        try {
            if (commandMap.containsKey(commandName)) {
                serverCollectionManager.addToHistory(commandName);
                logger.debug("Команда добавлена в историю: {}", commandName);
            }

            logger.info("Выполнение команды: {}", commandName);
            CommandResponse response = command.execute(argument, serverCollectionManager);

            if (response.isSuccess()) {
                logger.info("Команда '{}' выполнена успешно", commandName);
            } else {
                logger.warn("Команда '{}' не выполнена: {}", commandName, response.getResultMessage());
            }

            return response;
        } catch (Exception e) {
            logger.error("Ошибка при выполнении команды '{}': {}", commandName, e.getMessage(), e);
            return new CommandResponse("Ошибка при выполнении команды: " + e.getMessage(), false);
        }
    }
}
