package yath.sfwrtech4sa3.remindme;

import java.util.UUID;

public class Ally {
    public String ally_record_id;
    public String user_id;
    public String ally_id;

    public Ally() {}

    public Ally(String incoming_user_id, String incoming_ally_id) {
        this.ally_record_id = UUID.randomUUID().toString();
       this.user_id = incoming_user_id;
       this.ally_id = incoming_ally_id;
    }

    @Override
    public String toString() {
        return "Ally{" +
                "ally_record_id='" + ally_record_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", ally_id='" + ally_id + '\'' +
                '}';
    }
}
