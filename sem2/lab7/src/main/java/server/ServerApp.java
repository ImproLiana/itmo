package server;

import functions.DataBaseCollectionManager;
import myCommands.CommandRequest;
import myCommands.CommandResponse;
import myCommands.DBconnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

import static myCommands.DBconnector.getConnection;

public class ServerApp implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);
    private static final int PORT = 1532;
    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) throws SQLException {
        logger.info("Запуск сервера");

        logger.info("Попытка подключения к базе данных...");
        try (
                Connection connection = getConnection();
        ) {
            logger.info("Подключение к БД прошло успешно!");
        } catch (SQLException e) {
            logger.error("Ошибка подключения к базе данных: {}", e.getMessage());
            System.exit(1);
        }

        logger.info("Инициализация менеджеров и коллекции...");
        DataBaseCollectionManager collectionManager = new DataBaseCollectionManager();
        ServerCommandManager commandManager = new ServerCommandManager();

        logger.info("Запуск потока командной консоли сервера...");
        commandManager.startConsole(collectionManager);

        logger.info("Загрузка коллекции из БД...");
        collectionManager.loadCollection();
        logger.info("Коллекция загружена");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Завершение работы сервера по shutdown hook.");
        }));

        ExecutorService readPool = Executors.newFixedThreadPool(5);
        ExecutorService processPool = Executors.newFixedThreadPool(5);
        ExecutorService sendPool = Executors.newCachedThreadPool();

        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(PORT));
            logger.info("Сервер запущен и слушает порт {}...", PORT);

            while (true) {
                logger.debug("Ожидание команды от клиента...");
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                SocketAddress clientAddress = channel.receive(buffer);
                ByteBuffer requestData = ByteBuffer.wrap(buffer.array(), 0, buffer.position());

                logger.info("Пакет получен от {}", clientAddress);

                readPool.submit(() -> {
                    logger.debug("Задание чтения принято в readPool");
                    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(requestData.array()))) {
                        CommandRequest request = (CommandRequest) ois.readObject();
                        logger.info("Команда получена: {}", request.getCommandName());

                        processPool.submit(() -> {
                            logger.debug("Задание обработки принято в processPool: {}", request.getCommandName());
                            CommandResponse response = ServerCommandExecutor.execute(request, collectionManager);

                            sendPool.submit(() -> {
                                logger.debug("Задание отправки принято в sendPool");
                                try {
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                                    oos.writeObject(response);
                                    oos.flush();

                                    ByteBuffer responseBuffer = ByteBuffer.wrap(baos.toByteArray());
                                    synchronized (channel) {
                                        channel.send(responseBuffer, clientAddress);
                                    }

                                    logger.info("Ответ успешно отправлен клиенту: {}", clientAddress);
                                } catch (IOException e) {
                                    logger.error("Ошибка отправки ответа клиенту: {}", e.getMessage(), e);
                                }
                            });
                        });

                    } catch (IOException | ClassNotFoundException e) {
                        logger.error("Ошибка при чтении или десериализации запроса: {}", e.getMessage(), e);
                    }
                });
            }

        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода на сервере: {}", e.getMessage(), e);
        }
    }
}
