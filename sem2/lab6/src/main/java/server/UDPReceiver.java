package server;

import myCommands.CommandRequest;
import myCommands.CommandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPReceiver implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(UDPReceiver.class);
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 8192;

    public static void start(ServerCollectionManager serverCollectionManager) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(PORT));
            logger.info("Сервер слушает порт {} по UDP", PORT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                buffer.clear();

                SocketAddress clientAddress = channel.receive(buffer);
                if (clientAddress == null) {
                    logger.warn("Получен null-адрес от клиента. Пропускаем итерацию.");
                    continue;
                }

                buffer.flip();

                CommandRequest request;
                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array(), 0, buffer.limit()))) {
                    request = (CommandRequest) ois.readObject();
                }

                logger.info("Получена команда: {}", request.getCommandName());

                CommandResponse response = ServerCommandExecutor.execute(request, serverCollectionManager);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(response);
                    oos.flush();
                }

                ByteBuffer responseBuffer = ByteBuffer.wrap(baos.toByteArray());
                channel.send(responseBuffer, clientAddress);

                logger.info("Ответ отправлен клиенту.");
            }

        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода на сервере: {}", e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error("Ошибка десериализации команды: {}", e.getMessage(), e);
        }
    }
}
