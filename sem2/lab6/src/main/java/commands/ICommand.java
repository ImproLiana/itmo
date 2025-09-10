package commands;

import myCommands.CommandResponse;
import server.ServerCollectionManager;

public interface ICommand {
    /**
     * Выполняет команду с переданным аргументом и менеджером коллекции.
     * @param argument Аргумент, полученный от клиента (может быть null или объект)
     * @param manager Менеджер коллекции на сервере
     * @return Результат выполнения в виде CommandResponse
     */
    CommandResponse execute(Object argument, ServerCollectionManager manager);

    /**
     * @return Описание команды (для help)
     */
    String getDescription();

}
