package yath.sfwrtech4sa3.remindme;

import java.util.UUID;

public class Task {
    public String task_id;
    public String name;
    public String due_date;

    public Task() {}

    public Task(String incoming_name, String incoming_due_date) {
        this.task_id = UUID.randomUUID().toString();
        this.name = incoming_name;
        this.due_date = incoming_due_date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id='" + task_id + '\'' +
                ", name='" + name + '\'' +
                ", due_date='" + due_date + '\'' +
                '}';
    }
}
