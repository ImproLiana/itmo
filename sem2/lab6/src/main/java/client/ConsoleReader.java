package client;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import functions.FillObject;
import myCommands.CommandRequest;
import myCommands.CommandResponse;
import myCommands.UpdatePayload;
import progclasses.Dragon;

public class ConsoleReader implements Serializable {

    private static final Set<String> DRAGON_COMMANDS = Set.of("add", "add_id_min", "add_id_max");

    public void readConsole(CommandSender commandSender) throws ClassNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            String input;
            try {
                input = scanner.nextLine().trim();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("\nВвод прерван. Завершение работы клиента.");
                break;
            }

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String commandName = parts[0];
            String argument = parts.length > 1 ? parts[1] : null;

            // Обработка update_id
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

                CommandResponse checkResponse = commandSender.sendCommand(new CommandRequest("check_id_exists", id));
                if (!checkResponse.isSuccess()) {
                    System.out.println(checkResponse.getResultMessage());
                    continue;
                }

                Dragon dragon = new Dragon();
                FillObject.fill(dragon);
                UpdatePayload payload = new UpdatePayload(id, dragon);

                CommandResponse updateResponse = commandSender.sendCommand(new CommandRequest("update_id", payload));
                System.out.println(updateResponse != null ? updateResponse.getResultMessage() : "Ошибка: сервер не ответил.");
                continue;
            }

            // Обработка остальных команд
            Object argumentObject = argument;
            if (DRAGON_COMMANDS.contains(commandName)) {
                Dragon dragon = new Dragon();
                FillObject.fill(dragon);
                argumentObject = dragon;
            }

            CommandRequest request = new CommandRequest(commandName, argumentObject);
            CommandResponse response = commandSender.sendCommand(request);
            if (response != null) {
                System.out.println(response.getResultMessage());
            } else {
                System.out.println("Ошибка: сервер не ответил.");
            }

            if (commandName.equals("exit")) {
                break;
            }
        }
    }

    private ArrayList<String> promptCredentials(Scanner scanner) {
        ArrayList<String> creds = new ArrayList<>();

        System.out.print("Введите имя пользователя: ");
        try {
            creds.add(scanner.nextLine().trim());
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("\nВвод прерван.");
        }

        System.out.print("Введите пароль: ");
        try {
            creds.add(scanner.nextLine().trim());
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("\nВвод прерван.");
        }

        return creds;
    }
}
