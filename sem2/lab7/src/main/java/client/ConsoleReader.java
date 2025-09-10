package client;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import functions.DataBaseCollectionManager;
import functions.FillObject;
import myCommands.CommandRequest;
import myCommands.CommandResponse;
import myCommands.UpdatePayload;
import myCommands.UserManager;
import progclasses.Dragon;

import static functions.DataBaseCollectionManager.checkBelonging;

public class ConsoleReader implements Serializable {

    private final Set<String> DRAGON_COMMANDS = Set.of("add", "add_if_min", "add_if_max");
    private String username;
    private String password;

    public void readConsole(CommandSender commandSender) throws ClassNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);
        boolean authenticated = false;

        System.out.println("Добро пожаловать! Введите команду \"register\" или \"login\".");

        while (!authenticated) {
            System.out.print(">>> ");
            String input;
            try {
                input = scanner.nextLine().trim();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("\nВвод прерван. Завершение работы клиента.");
                return;
            }

            if (input.isEmpty()) {
                System.out.println("Введите \"register\" или \"login\".");
                continue;
            }

            String command = input.split("\\s+")[0].toLowerCase();

            if (command.equals("register") || command.equals("login")) {
                promptCredentials(scanner);
                ArrayList<String> info = new ArrayList<>();
                info.add(username);
                info.add(password);
                CommandRequest request = new CommandRequest(command, info, username, password);
                CommandResponse response = commandSender.sendCommand(request);
                System.out.println(response.getResultMessage());
                authenticated = response.isSuccess();
            } else if (command.equals("exit")) {
                return;
            } else {
                System.out.println("Неизвестная команда. Введите \"register\" или \"login\".");
            }
        }

        // === Основной цикл команд ===
        while (true) {
            System.out.print(">>> ");
            String input;
            try {
                input = scanner.nextLine().trim();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("\nВвод прерван. Завершение работы клиента.");
                break;
            }

            if (input.isEmpty()) {
                System.out.println("Введите команду. Для справки введите \"help\".");
                continue;
            }

            String[] parts = input.split("\\s+", 2);
            String commandName = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : null;

            if (commandName.equals("exit")) break;


            if (commandName.equals("update_id")) {
                if (argument == null) {
                    System.out.println("Вы не указали ID.");
                    continue;
                }

                Long id;

                try {
                    id = Long.parseLong(argument);
                } catch (NumberFormatException e) {
                    System.out.println("ID должен быть числом.");
                    continue;
                }

                CommandResponse checkResponse = commandSender.sendCommand(
                        new CommandRequest("check_id_exists", id, username, password)
                );
                if (!checkResponse.isSuccess()) {
                    System.out.println(checkResponse.getResultMessage());
                    continue;
                }

                if (!checkBelonging(id, username)){
                    System.out.println("Дракон принадлежит другому пользователю");
                    continue;
                }

                Dragon updatedDragon = new Dragon();
                FillObject.fill(updatedDragon);
                UpdatePayload payload = new UpdatePayload(id, updatedDragon);

                CommandResponse updateResponse = commandSender.sendCommand(
                        new CommandRequest("update_id", payload, username, password)
                );
                System.out.println(updateResponse != null ? updateResponse.getResultMessage() : "Ошибка: сервер не ответил.");
                continue;
            }

            if (commandName.equals("login") || commandName.equals("register")){
                System.out.println("Вы уже вошли в систему");
                continue;
            }

            Object argumentObject = argument;
            if (DRAGON_COMMANDS.contains(commandName)) {
                Dragon dragon = new Dragon();
                FillObject.fill(dragon);
                argumentObject = dragon;
            }

            CommandRequest request = new CommandRequest(commandName, argumentObject, username, password);
            CommandResponse response = commandSender.sendCommand(request);

            if (response != null) {
                System.out.println(response.getResultMessage());
            } else {
                System.out.println("Ошибка: сервер не ответил.");
            }
        }
    }

    private void promptCredentials(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        try {
            username = scanner.nextLine().trim();
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("\nВвод прерван.");
        }

        System.out.print("Введите пароль: ");
        try {
            password = UserManager.hash(scanner.nextLine().trim());
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("\nВвод прерван.");
        }
    }
}
