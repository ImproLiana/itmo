package commands;

import functions.DataBaseCollectionManager;
import myCommands.CommandResponse;
import myCommands.DBconnector;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUser implements ICommand, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(LoginUser.class);

    @Override
    public CommandResponse execute(Object argument, DataBaseCollectionManager manager, String username1){
        String username;
        String password;

        try {
            ArrayList<String> creds = (ArrayList<String>) argument;
            username = creds.get(0);
            password = creds.get(1);
        } catch (Exception e) {
            return new CommandResponse("Авторизация неуспешна: переданы некорректные данные", false);
        }


        String passwordHash = password;
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = DBconnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                logger.info("Пользователь '{}' успешно авторизован", username);
                return new CommandResponse("Пользователь '" + username + "' успешно авторизован", true);
            } else {
                logger.warn("Неуспешная попытка входа для пользователя '{}'", username);
                return new CommandResponse("Неверный логин или пароль", false);
            }

        } catch (SQLException e) {
            logger.error("Ошибка при авторизации пользователя '{}': {}", username, e.getMessage());
            return new CommandResponse("Ошибка при авторизации пользователя '" + username + "': " + e.getMessage(), false);
        }
    }

    @Override
    public String getDescription() {
        return "Авторизация пользователя";
    }
}
