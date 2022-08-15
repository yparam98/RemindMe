package yath.sfwrtech4sa3.remindme;

import java.util.List;
import java.util.UUID;

public class TaskList {
    public String taskListID;
    public String userID;
    public List<Task> tasks;

    public TaskList(String incoming_userID) {
        this.taskListID = UUID.randomUUID().toString();
        this.userID = incoming_userID;
    }

    public void addTask(Task incoming_task) {
        this.tasks.add(incoming_task);
    }
}
