package myCommands;

import java.io.Serializable;

public class CommandRequest implements Serializable{
    private String commandName;
    private Object argument;
    private String username;
    private String password;

    public CommandRequest(String commandName, Object argument, String username, String password) {
        this.commandName = commandName;
        this.argument = argument;
        this.username = username;
        this.password = password;
    }

    public String getCommandName() {
        return commandName;
    };
    
    public Object getArgument() {
        return argument;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
