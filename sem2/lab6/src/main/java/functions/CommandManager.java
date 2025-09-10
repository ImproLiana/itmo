//package functions;
//
//import java.util.*;
//import commands.*;
//
///**
// * Менеджер команд для управления и выполнения операций с коллекцией.
// * Обеспечивает регистрацию, хранение и выполнение команд, а также ведение истории.
// */
//public class CommandManager {
//    private final CollectionManager collectionManager = new CollectionManager();
//    private final LinkedHashMap<String, ICommand> commands = new LinkedHashMap<>();
//    private final Queue<String> commandHistory;
//    private static final ArrayList<String> commandsWithArguments = new ArrayList<>();
//
//    /**
//     * Инициализирует менеджер команд, регистрируя все доступные команды.
//     * @throws Exception при ошибках инициализации команд
//     */
//    public CommandManager() throws Exception {
//        // Регистрация команд
//        registerCommand("add", new AddElementCommand());
//        registerCommand("add_if_min", new AddIfMinCommand());
//        registerCommand("add_if_max", new AddIfMaxCommand());
//        registerCommand("clear", new ClearCommand());
////        registerCommand("execute_script", new ExecuteScriptCommand());
//        registerCommand("exit", new ExitCommand());
//        registerCommand("count_greater_than_character", new CountGTCCommand());
//        registerCommand("help", new HelpCommand());
//        registerCommand("history", new HistoryCommand());
//        registerCommand("info", new InfoCommand());
//        registerCommand("print_ascending", new PrintAscendingCommand());
//        registerCommand("print_field_descending_color", new PrintFieldDescendingColorCommand());
//        registerCommand("remove_by_id", new RemoveByIdCommand());
//        registerCommand("save", new SaveCommand());
//        registerCommand("show", new ShowCommand());
//        registerCommand("update_id", new UpdateIdCommand());
//
//        // Команды, требующие аргументов
//        registerCommandWithArgument("add");
//        registerCommandWithArgument("add_if_min");
//        registerCommandWithArgument("add_if_max");
//        registerCommandWithArgument("execute_script");
//        registerCommandWithArgument("count_greater_than_character");
//        registerCommandWithArgument("remove_by_id");
//        registerCommandWithArgument("update_id");
//
//        commandHistory = new LinkedList<>();
//    }
//
//    /**
//     * Выполняет команду по строке ввода.
//     * @param input строка с командой и аргументами
//     */
//    public void execute(String input) {
//        String[] args = input.split(" ");
//        String commandName = args[0];
//        addToHistory(commandName);
//
//        if (commands.containsKey(commandName)) {
//            ICommand command = commands.get(commandName);
//            args = Arrays.copyOfRange(args, 1, args.length);
//            try {
//                command.run(args, collectionManager, this);
//            } catch (Exception e) {
//                System.out.println("Ошибка выполнения команды: " + e.getMessage());
//            }
//        } else {
//            System.out.println("Команда '" + input + "' не существует");
//        }
//    }
//
//    /**
//     * Добавляет команду в историю (максимум 12 последних команд).
//     * @param command имя выполненной команды
//     */
//    private void addToHistory(String command) {
//        if (commandHistory.size() >= 12) {
//            commandHistory.poll();
//        }
//        commandHistory.offer(command);
//    }
//
//    /**
//     * Регистрирует новую команду.
//     * @param name имя команды
//     * @param command объект команды
//     */
//    private void registerCommand(String name, ICommand command) {
//        commands.put(name, command);
//    }
//
//    /**
//     * Регистрирует команду, требующую аргументов.
//     * @param name имя команды
//     */
//    private void registerCommandWithArgument(String name) {
//        commandsWithArguments.add(name);
//    }
//
//    /** @return историю выполненных команд */
//    public Queue<String> getCommandHistory() {
//        return commandHistory;
//    }
//
//    /** @return зарегистрированные команды */
//    public LinkedHashMap<String, ICommand> getCommandHashMap() {
//        return commands;
//    }
//
//    /** @return менеджер коллекции */
//    public CollectionManager getCollectionManager() {
//        return collectionManager;
//    }
//
//    /** @return список команд, требующих аргументов */
//    public static ArrayList<String> getCommandsWithArguments() {
//        return commandsWithArguments;
//    }
//
//    /**
//     * Загружает коллекцию из указанного файла.
//     * @param path путь к файлу с коллекцией
//     */
//    public void start(String path) {
//        collectionManager.loadCollection(path);
//    }
//
//    /**
//     * Возвращает команду по имени.
//     * @param command имя команды
//     * @return объект команды или null, если не найдена
//     */
//    public ICommand getCommand(String command) {
//        return commands.get(command);
//    }
//}