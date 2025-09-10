package functions;

import myCommands.DBconnector;
import progclasses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DataBaseCollectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseCollectionManager.class);
    private final HashSet<Dragon> collection = new HashSet<>();
    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> historyByUser = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    private void add(PreparedStatement statement, Dragon dragon, String username) throws SQLException {
        lock.lock();
        try {
            statement.setString(1, dragon.getName());
            statement.setLong(2, dragon.getCoordinates().getX());
            statement.setLong(3, dragon.getCoordinates().getY());
            if (dragon.getCreationDate() == null) {
                dragon.setCreationDate(LocalDate.now());
            }
            statement.setDate(4, java.sql.Date.valueOf(dragon.getCreationDate()));
            statement.setLong(5, dragon.getAge());
            statement.setString(6, dragon.getColor().toString());
            if(dragon.getType() != null) {
                statement.setString(7, dragon.getType().toString());
            } else{
                statement.setString(7, null);
            }
            statement.setString(8, dragon.getCharacter().toString());
            if (dragon.getKiller() != null) {
                statement.setString(9, dragon.getKiller().getName());
                statement.setString(10, dragon.getKiller().getPassportID());
                statement.setLong(11, dragon.getKiller().getLocation().getX());
                statement.setDouble(12, dragon.getKiller().getLocation().getY());
                statement.setLong(13, dragon.getKiller().getLocation().getZ());
                statement.setString(14, dragon.getKiller().getLocation().getName());
            } else {
                statement.setNull(9, Types.VARCHAR);
                statement.setNull(10, Types.VARCHAR);
                statement.setNull(11, Types.BIGINT);
                statement.setNull(12, Types.DOUBLE);
                statement.setNull(13, Types.BIGINT);
                statement.setNull(14, Types.VARCHAR);
            }
            statement.setString(15, username);
        } finally {
            lock.unlock();
        }
    }

    public HashSet<Dragon> getCollection() {
        lock.lock();
        try {
            return new HashSet<>(collection);
        } finally {
            lock.unlock();
        }

    }

    public void loadCollection(){
        lock.lock();
            String sql = "SELECT * FROM dragons;";
            try (
                    Connection conn = DBconnector.getConnection();
                    Statement statement = conn.createStatement();
                    ResultSet result = statement.executeQuery(sql);
            ) {
                while (result.next()) {
                    Dragon dragon = new Dragon();
                    dragon.setId(result.getLong("id"));
                    dragon.setName(result.getString("name"));
                    dragon.setCoordinates(new Coordinates(result.getLong("coordinates_x"), result.getLong("coordinates_y")));
                    dragon.setCreationDate(LocalDate.parse(result.getString("creation_date")));
                    dragon.setAge(result.getLong("age"));
                    dragon.setColor(Color.set(result.getString("color")));
                    dragon.setType(result.getString("type") == null ? null : DragonType.set(result.getString("type")));
                    dragon.setCharacter(DragonCharacter.set(result.getString("character")));
                    if (result.getString("killer_name") != null) {
                        dragon.setKiller(new Person(
                                result.getString("killer_name"),
                                result.getString("killer_passport_id"),
                                new Location(
                                        result.getLong("killer_location_x"),
                                        result.getDouble("killer_location_y"),
                                        result.getLong("killer_location_z"),
                                        result.getString("killer_location_name")
                                )
                        ));
                    }
                    dragon.setOwner(result.getString("owner_username"));
                    collection.add(dragon);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при загрузке коллекции из БД", e);
        } finally {
            lock.unlock();
        }
    }

    public boolean addToDB(Dragon dragon, String username, Long id) {
        lock.lock();
        String sql;
        if (id != null){
            sql = "INSERT INTO dragons (name, coordinates_x, coordinates_y, creation_date, age, color, type, character, killer_name, killer_passport_id, killer_location_x, killer_location_y, killer_location_z, killer_location_name, owner_username, id) VALUES (?, ?, ?, ?, ?, ?::dragon_color, ?::dragon_type, ?::dragon_character, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO dragons (name, coordinates_x, coordinates_y, creation_date, age, color, type, character, killer_name, killer_passport_id, killer_location_x, killer_location_y, killer_location_z, killer_location_name, owner_username) VALUES (?, ?, ?, ?, ?, ?::dragon_color, ?::dragon_type, ?::dragon_character, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        }
        try (
                Connection conn = DBconnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            if (id != null) {
                add(statement, dragon, username);
                statement.setLong(16, id);
                dragon.setId(id);
                dragon.setOwner(username);
                statement.executeUpdate();
                collection.add(dragon);
                logger.debug("Дракон с ID {} добавлен", id);
                return true;
            } else {
                add(statement, dragon, username);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    long generatedId = rs.getLong("id");
                    dragon.setId(generatedId);
                    dragon.setOwner(username);
                    collection.add(dragon);
                    logger.debug("Дракон с ID {} добавлен в коллекцию", dragon.getId());
                    return true;
                } else {
                    logger.warn("Произошла ошибка. Дракон с ID {} не добавлен", dragon.getId());
                    return false;
                }
            }
        } catch (SQLException e){
            logger.warn("Ошибка при загрузке коллекции в БД", e);
        } finally {
            lock.unlock();
        }
        return false;
    }

    public boolean removeFromDB(Long id){
        lock.lock();
        String sql = "DELETE FROM dragons WHERE id = ?";
        try (
                Connection conn = DBconnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            if (rows == 1){
                collection.removeIf(dragon -> id.equals(dragon.getId()));

                logger.debug("Дракон с ID {} удалён", id);
                return true;
            } else {
                logger.debug("Дракон с ID {} не найден, удаление не выполнено", id);

                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static boolean checkBelonging(Long id, String username){
//        lock.lock();
        String sql = "SELECT owner_username FROM dragons WHERE id = ?";
        try (
                Connection conn = DBconnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Objects.equals(resultSet.getString("owner_username"), username);
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            lock.unlock();
//        }
    }

    public boolean updateById(Long id, Dragon newDragon, String username){
        lock.lock();
        try {
            boolean removed = removeFromDB(id);
            if (removed) {
                addToDB(newDragon, username, id);
                logger.info("Дракон с ID {} обновлён", id);
            } else {
                logger.warn("Обновление не выполнено: дракон с ID {} не найден", id);
            }
            return removed;
        } finally {
            lock.unlock();
        }
    }

    public boolean shouldAddIfMin(Dragon dragon) {
        return collection.isEmpty() || dragon.compareTo(getMinDragon()) < 0;
    }

    public boolean shouldAddIfMax(Dragon dragon) {
        return collection.isEmpty() || dragon.compareTo(getMaxDragon()) > 0;
    }

    public Dragon getMinDragon() {
        lock.lock();
        try {
            return collection.stream().min(Comparator.naturalOrder()).orElse(null);
        }finally {
            lock.unlock();
        }
    }

    public Dragon getMaxDragon() {
        lock.lock();
        try {
            return collection.stream().max(Comparator.naturalOrder()).orElse(null);
        } finally {
            lock.unlock();
        }
    }

    public void addToHistory(String username, String command) {
        var deque = historyByUser.computeIfAbsent(username, u -> new ConcurrentLinkedDeque<>());
        deque.addLast(command);
        while (deque.size() > 12) {
            deque.pollFirst();
        }
        logger.debug("Команда '{}' добавлена в историю пользователя {}", command, username);
    }

    public LinkedList<String> getCommandHistory(String username) {
        var deque = historyByUser.get(username);
        return deque == null ? new LinkedList<>() : new LinkedList<>(deque);
    }

    public boolean clearCollection(String username) throws SQLException {
        lock.lock();
        String sql = "DELETE FROM dragons WHERE owner_username = ?";
        try (
                Connection conn = DBconnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setString(1, username);
            int res = statement.executeUpdate();
            if (res >= 1){
                collection.removeIf(dragon -> username.equals(dragon.getOwner()));
                logger.info("Удалено {} драконов, принадлежащих пользователю {}", res, username);
                return true;
            } else {
                logger.info("Не найдены драконы, принадлежащие пользователю {} ", username);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при очистке коллекции пользователя {}: {}", username, e.getMessage());
        }
        finally {
            lock.unlock();
        }
        return false;
    }
}

