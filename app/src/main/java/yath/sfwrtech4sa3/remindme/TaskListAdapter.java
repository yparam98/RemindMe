package yath.sfwrtech4sa3.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TaskListAdapter extends AppCompatActivity {
    private ArrayAdapter<Task> taskArrayAdapter;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_adapter);
    }

    public TaskListAdapter() { }

    public TaskListAdapter(TaskList taskList) {
        this.taskArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_2,
                taskList.tasks
        );

        this.taskListView = (ListView) findViewById(R.id.tasks_list_view);
        this.taskListView.setAdapter(this.taskArrayAdapter);
    }
}