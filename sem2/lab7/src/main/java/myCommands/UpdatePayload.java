package myCommands;

import java.io.Serializable;

import progclasses.Dragon;

public class UpdatePayload implements Serializable {
    private long id;
    private Dragon dragon;

    public UpdatePayload(long id, Dragon dragon) {
        this.id = id;
        this.dragon = dragon;
    }

    public long getId() {
        return id;
    }

    public Dragon getDragon() {
        return dragon;
    }
}
 