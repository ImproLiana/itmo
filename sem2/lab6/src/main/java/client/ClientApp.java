package client;

import java.io.IOException;
import java.io.Serializable;

public class ClientApp implements Serializable {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        CommandSender commandSender = new CommandSender("localhost", 1532);
        ConsoleReader consoleReader = new ConsoleReader();

        consoleReader.readConsole(commandSender);
    }
}

