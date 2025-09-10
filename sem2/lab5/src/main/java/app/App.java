package app;

import functions.CommandsGetter;

/**
 * Главный класс приложения для обработки команд из файла.
 * Принимает путь к файлу в качестве аргумента командной строки.
 */
public class App {
    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки (первый - путь к файлу с коллекцией)
     * @throws Exception если возникла ошибка при обработке файла
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Пожалуйста, укажите имя файла в аргументах командной строки.");
            return;
        }
        String path = args[0];
        CommandsGetter commandsGetter = new CommandsGetter(path);
        commandsGetter.start();
    }
}