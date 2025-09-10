package server;

import commands.LoadCollectionCommand;
import commands.SaveCommand;
import myCommands.CommandRequest;
import myCommands.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import functions.WorkWithFile;
import progclasses.Dragon;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.SQLException;

public class ServerApp implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);
    private static final int PORT = 1532;
    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        ServerCollectionManager collectionManager = new ServerCollectionManager();
        ServerCommandManager commandManager = new ServerCommandManager();
        commandManager.startConsole(collectionManager);

        if (args.length < 1){
            logger.error("Не был получен файл коллекции! ");
        } else if (args.length == 1) {
            LoadCollectionCommand loadCollectionCommand = new LoadCollectionCommand();
            loadCollectionCommand.execute(args[0], collectionManager);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Завершение работы сервера. Сохраняем коллекцию...");
            new SaveCommand().execute(collectionManager.getPath(), collectionManager);
        }));

        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(PORT));
            logger.info("Сервер запущен. Ожидание команд...");

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                buffer.clear();
                SocketAddress clientAddress = channel.receive(buffer);
                buffer.flip();

                CommandRequest request;
                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()))) {
                    request = (CommandRequest) ois.readObject();
                }

                logger.info("Получена команда: {}", request.getCommandName());

                CommandResponse response = ServerCommandExecutor.execute(request, collectionManager);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(response);
                    oos.flush();
                }

                ByteBuffer responseBuffer = ByteBuffer.wrap(baos.toByteArray());
                channel.send(responseBuffer, clientAddress);

                logger.info("Ответ отправлен клиенту.\n");
            }

        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода на сервере: {}", e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error("Ошибка десериализации: {}", e.getMessage(), e);
        }
    }
}
