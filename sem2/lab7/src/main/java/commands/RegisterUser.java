package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import myCommands.DBconnector;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static myCommands.UserManager.userExists;

public class RegisterUser implements ICommand, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUser.class);

    /**
     * Хеширует строку с использованием алгоритма SHA-512 и кодированием Base64.
     *
     * @param input строка
     * @return хешированная строка
     */

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username1) {
        String username;
        String password;

        // Проверка и извлечение аргументов
        try {
            ArrayList<String> creds = (ArrayList<String>) argument;
            username = creds.get(0);
            password = creds.get(1);
        } catch (Exception e) {
            logger.warn("Некорректные данные при попытке регистрации: {}", e.getMessage());
            return new CommandResponse("Регистрация неуспешна: переданы некорректные данные", false);
        }

        // Проверка на существование пользователя
        if (userExists(username)) {
            logger.info("Попытка регистрации существующего пользователя '{}'", username);
            return new CommandResponse("Регистрация отклонена: пользователь '" + username + "' уже существует", false);
        }

        // Хеширование пароля
        String passwordHash = password;
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        // Попытка записи в базу данных
        try (Connection conn = DBconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();

            logger.info("Пользователь '{}' успешно зарегистрирован", username);
            return new CommandResponse("Регистрация пользователя '" + username + "' прошла успешно", true);

        } catch (SQLException e) {
            logger.error("Ошибка при регистрации пользователя '{}': {}", username, e.getMessage());
            return new CommandResponse("Ошибка при регистрации пользователя '" + username + "': " + e.getMessage(), false);
        }
    }

    @Override
    public String getDescription() {
        return "Регистрация нового пользователя";
    }
}
