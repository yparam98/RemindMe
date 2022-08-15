package yath.sfwrtech4sa3.remindme;

import java.util.UUID;

public class Task {
    public String task_id;
    public String name;
    public String description;
    public String created_date;
    public String due_date;
    public String userID;

    public Task() {}

    public Task(String incoming_name, String incoming_desc, String incoming_created_date, String incoming_due_date, String incoming_uid) {
        this.task_id = UUID.randomUUID().toString();
        this.name = incoming_name;
        this.description = incoming_desc;
        this.created_date = incoming_created_date;
        this.due_date = incoming_due_date;
        this.userID = incoming_uid;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id='" + task_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created_date='" + created_date + '\'' +
                ", due_date='" + due_date + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
