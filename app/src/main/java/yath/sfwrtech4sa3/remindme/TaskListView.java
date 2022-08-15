package yath.sfwrtech4sa3.remindme;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

interface TaskListCallback {
    void getTaskList(boolean isValid, TaskList taskList);
}

public class TaskListView extends Fragment {
    private String uid = "";
    private TaskList taskList;

    public TaskListView() {
        // Required empty public constructor
    }

    public TaskListView(String uid) {
        this.uid = uid;
        TaskList taskList = new TaskList(uid);
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.getTaskList(uid, new TaskListCallback() {
            @Override
            public void getTaskList(boolean isValid, TaskList taskList) {
                if (isValid) {
                    TaskListAdapter taskListAdapter = new TaskListAdapter(taskList);
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.add_task);

        Intent taskBuilderIntent = new Intent(getContext(), TaskBuilder.class);
        taskBuilderIntent.putExtra("UID", this.uid);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(taskBuilderIntent);
            }
        });
    }
}