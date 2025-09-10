package myCommands;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;

public class UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    public static boolean register(String username, String password) {
        if (userExists(username)) {
            logger.warn("Регистрация отклонена: пользователь '{}' уже существует", username);
            return false;
        }
        String passwordHash = password;
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try (Connection conn = DBconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
            logger.info("Регистрация пользователя '{}' прошла успешно", username);
            return true;

        } catch (SQLException e) {
            logger.error("Ошибка при регистрации пользователя '{}': {}", username, e.getMessage());
            return false;
        }
    }

    public static boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = DBconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            boolean success = rs.next();
            if (success) {
                logger.info("Пользователь '{}' успешно авторизован", username);
            } else {
                logger.warn("Неуспешная попытка входа для пользователя '{}'", username);
            }

            return success;

        } catch (SQLException e) {
            logger.error("Ошибка при авторизации пользователя '{}': {}", username, e.getMessage());
            return false;
        }
    }

    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA-384 не поддерживается: {}", e.getMessage());
            throw new RuntimeException("SHA-384 не поддерживается", e);
        }
    }

    public static boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DBconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            logger.error("Ошибка при проверке существования пользователя '{}': {}", username, e.getMessage());
            return true;
        }
    }

    public static boolean checkUsersPassword(String username, String password){
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (
                Connection conn = DBconnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Objects.equals(resultSet.getString("password_hash"), password);
            } else {
                return false;
            }

        } catch (SQLException e) {
            logger.error("При проверке пользователя произошла ошибка");
            throw new RuntimeException(e);
        }
    }
}
