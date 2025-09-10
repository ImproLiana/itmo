package commands;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import exception.BadArgumentException;
import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import progclasses.*;
import server.ServerCommandManager;

public class ExecuteScriptCommand implements ICommand {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteScriptCommand.class);
    private static final Set<String> scriptStack = new HashSet<>();
    private static final List<String> complexCommands = List.of("add", "add_if_min", "add_if_max", "update_id", "execute_script");

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username) {
        if (argument == null) {
            logger.warn("Попытка выполнить скрипт без указания пути");
            return new CommandResponse("Путь к скрипту не обнаружен", false);
        }

        String path = (String) argument;
        String canonicalPath;

        try {
            canonicalPath = new File(path).getCanonicalPath();
        } catch (IOException e) {
            logger.error("Ошибка получения канонического пути: {}", e.getMessage());
            return new CommandResponse("Ошибка пути к скрипту: " + e.getMessage(), false);
        }

        if (scriptStack.contains(canonicalPath)) {
            logger.error("Обнаружена рекурсия при выполнении скрипта: {}", canonicalPath);
            return new CommandResponse("Обнаружена рекурсия! Скрипт '" + canonicalPath + "' уже выполняется.", false);
        }

        scriptStack.add(canonicalPath);
        StringBuilder fullLog = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(canonicalPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                fullLog.append("\n>> Выполняется команда: ").append(line).append("\n");
                logger.info("Выполнение команды из скрипта: {}", line);

                String[] parts = line.split(" ", 2);
                String command = parts[0];

                CommandResponse response;

                if (complexCommands.contains(command)) {
                    response = processComplexCommand(reader, command, parts, manager, username);
                } else {
                    ICommand cmd = ServerCommandManager.getCommands().get(command);
                    if (cmd == null) {
                        logger.warn("Команда не найдена: {}", command);
                        response = new CommandResponse("Команда '" + command + "' не найдена", false);
                    } else {
                        Object arg = parts.length > 1 ? parts[1] : null;
                        response = cmd.execute(arg, manager, username);
                    }
                }

                if (response != null) {
                    fullLog.append(response.getResultMessage()).append("\n");
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка чтения скрипта {}: {}", canonicalPath, e.getMessage());
            return new CommandResponse("Ошибка чтения файла: " + e.getMessage(), false);
        } catch (BadArgumentException e) {
            logger.error("Ошибка выполнения команды в скрипте: {}", e.getMessage());
            return new CommandResponse("Ошибка выполнения: " + e.getMessage(), false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            scriptStack.remove(canonicalPath);
        }

        return new CommandResponse(fullLog.toString().trim(), true);
    }

    private CommandResponse processComplexCommand(BufferedReader reader, String command, String[] parts, DataBaseCollectionManager manager, String username)
            throws IOException, BadArgumentException {
        switch (command) {
            case "add": {
                Dragon dragon = readDragonFromScript(reader);
                manager.addToDB(dragon, username, null);
                return new CommandResponse("Дракон успешно добавлен!", true);
            }
            case "add_if_max": {
                Dragon dragon = readDragonFromScript(reader);
                Optional<Dragon> max = manager.getCollection().stream().max(Dragon::compareTo);
                if (max.isEmpty() || dragon.compareTo(max.get()) > 0) {
                    manager.addToDB(dragon, username, null);
                    return new CommandResponse("Дракон добавлен как максимальный", true);
                }
                return new CommandResponse("Дракон не добавлен: не максимальный", true);
            }
            case "add_if_min": {
                Dragon dragon = readDragonFromScript(reader);
                Optional<Dragon> min = manager.getCollection().stream().min(Dragon::compareTo);
                if (min.isEmpty() || dragon.compareTo(min.get()) < 0) {
                    manager.addToDB(dragon, username, null);
                    return new CommandResponse("Дракон добавлен как минимальный", true);
                }
                return new CommandResponse("Дракон не добавлен: не минимальный", true);
            }
            case "update_id": {
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    return new CommandResponse("ID не указан", false);
                }
                long userId;
                try {
                    userId = Long.parseLong(parts[1].trim());
                } catch (NumberFormatException e) {
                    return new CommandResponse("ID должен быть числом", false);
                }
                Dragon updatedDragon = readDragonFromScript(reader);
                if (!DataBaseCollectionManager.checkBelonging(userId, username)) {
                    return new CommandResponse("Невозможно обновить: дракон не принадлежит вам", false);
                } else {
                    manager.updateById(userId, updatedDragon, username);
                    return new CommandResponse("Дракон с ID " + userId + " обновлён", true);
                }
            }
            case "execute_script": {
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    return new CommandResponse("Путь к вложенному скрипту не указан", false);
                }
                String nestedPath = parts[1].trim();
                CommandResponse nestedResponse = this.execute(nestedPath, manager, username);
                return new CommandResponse("Вложенный скрипт: " + nestedPath + "\n" + nestedResponse.getResultMessage(), nestedResponse.isSuccess());
            }
            case "login", "register": {
                return new CommandResponse("Вы уже в системе", false);
            }
        }
        return null;
    }

    private Dragon readDragonFromScript(BufferedReader reader) throws IOException {
        String name = readNonEmptyLine(reader, "Имя дракона не задано");
        String[] coords = readNonEmptyLine(reader, "Координаты не заданы").split(" ");
        long x = Long.parseLong(coords[0]);
        long y = Long.parseLong(coords[1]);
        Coordinates coordinates = new Coordinates(x, y);

        long age = Long.parseLong(readNonEmptyLine(reader, "Возраст не задан"));
        Color color = Color.valueOf(readNonEmptyLine(reader, "Цвет не задан").toUpperCase());
        DragonCharacter character = DragonCharacter.valueOf(readNonEmptyLine(reader, "Характер не задан").toUpperCase());

        String typeLine = readLine(reader);
        DragonType type = (typeLine != null && !typeLine.equalsIgnoreCase("null")) ? DragonType.valueOf(typeLine.toUpperCase()) : null;

        String killerName = readLine(reader);
        Person killer = null;
        if (killerName != null && !killerName.equalsIgnoreCase("null")) {
            String passport = readLine(reader);
            String[] loc = readNonEmptyLine(reader, "Локация не задана").split(" ");
            Location location = new Location(Long.parseLong(loc[0]), Double.parseDouble(loc[1]), Long.parseLong(loc[2]), loc[3]);
            killer = new Person(killerName, passport.isBlank() ? null : passport, location);
        }

        Dragon d = new Dragon();
        d.setName(name);
        d.setCoordinates(coordinates);
        d.setAge(age);
        d.setColor(color);
        d.setCharacter(character);
        d.setType(type);
        d.setKiller(killer);

        return d;
    }

    private String readNonEmptyLine(BufferedReader reader, String errorMessage) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) return line;
        }
        throw new IOException(errorMessage);
    }

    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        return line != null ? line.trim() : null;
    }

    @Override
    public String getDescription() {
        return "исполнить скрипт из указанного файла";
    }
}
