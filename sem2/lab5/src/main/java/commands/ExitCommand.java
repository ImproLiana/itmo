package commands;

import java.io.IOException;
import functions.CollectionManager;
import functions.CommandManager;

/**
 * Команда для завершения работы программы.
 * Немедленно останавливает выполнение программы без сохранения данных.
 */
public class ExitCommand implements ICommand {
    /**
     * Выполняет завершение работы программы.
     * @param args аргументы команды (не используются)
     * @param collectionManager менеджер коллекции (не используется)
     * @param commandManager менеджер команд (не используется)
     * @throws IOException никогда не выбрасывается (оставлено для совместимости)
     */
    @Override
    public void run(String[] args, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        System.exit(1);
    }

    /** @return имя команды "exit" */
    @Override
    public String getName() {
        return "exit";
    }

    /** @return описание команды */
    @Override
    public String getDescription() {
        return "завершение программы (без сохранения в файл)";
    }
}