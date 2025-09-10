package myCommands;

import java.io.Serializable;

public class CommandResponse implements Serializable {
    private String resultMessage;
    private boolean success;

    public CommandResponse(String resultMessage, boolean success) {
        this.resultMessage = resultMessage;
        this.success = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public boolean isSuccess() {
        return success;
    }
}
