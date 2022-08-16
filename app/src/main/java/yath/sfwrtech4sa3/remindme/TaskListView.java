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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskListView extends Fragment {
    private TaskList taskList;
    private User currentUser;

    public TaskListView() {
        // Required empty public constructor
    }

    public TaskListView(User user) {
        this.currentUser = user;
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
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.getTasks(this.currentUser.uid, new TaskListCallback() {
            @Override
            public void getTaskList(boolean isValid, List<Task> tasks) {
                if (isValid) {
                    for (int i = 0; i < tasks.size(); i++) {
                        Log.d("yathavan", tasks.get(i).toString());
                    }
                }
            }
        });

//        ExtendedFloatingActionButton add_task_button = getView().findViewById(R.id.add_task_button);
//
//        Intent taskBuilderIntent = new Intent(getContext(), TaskBuilder.class);
//        taskBuilderIntent.putExtra("UID", this.uid);
//
//        add_task_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(taskBuilderIntent);
//            }
//        });
    }
}