package client;

import java.net.*;
import java.io.*;

import myCommands.CommandRequest;
import myCommands.CommandResponse;

public class CommandSender implements Serializable {
    private final String serverHost;
    private final int serverPort;

    public CommandSender(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    public CommandResponse sendCommand(CommandRequest request) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(3000); // 3 секунды ожидания ответа

            // Сериализация объекта запроса
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(byteStream)) {
                oos.writeObject(request);
            }

            byte[] sendData = byteStream.toByteArray();
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Приём ответа
            byte[] receiveData = new byte[8192];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            // Десериализация ответа
            ByteArrayInputStream inputStream = new ByteArrayInputStream(
                    receivePacket.getData(), 0, receivePacket.getLength()
            );
            try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
                Object response = ois.readObject();
                if (response instanceof CommandResponse) {
                    return (CommandResponse) response;
                } else {
                    return new CommandResponse("Ошибка: получен некорректный ответ от сервера.", false);
                }
            }

        } catch (SocketTimeoutException e) {
            return new CommandResponse("Сервер не ответил за 3 секунды. Возможно, он временно недоступен.", false);
        } catch (IOException | ClassNotFoundException e) {
            return new CommandResponse("Ошибка при соединении с сервером: " + e.getMessage(), false);
        }
    }
}
