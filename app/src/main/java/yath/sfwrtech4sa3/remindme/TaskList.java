package yath.sfwrtech4sa3.remindme;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList {
    public String taskListID;
    public String userID;
    public List<Task> tasks;

    public TaskList() {}

    public TaskList(String incoming_userID) {
        this.taskListID = "t-"+incoming_userID;
        this.userID = incoming_userID;
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task incoming_task) {
        this.tasks.add(incoming_task);
    }
}
