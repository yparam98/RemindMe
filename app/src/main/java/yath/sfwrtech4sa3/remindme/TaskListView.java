package yath.sfwrtech4sa3.remindme;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.grpc.HandlerRegistry;

public class TaskListView extends Fragment {
    private TaskList taskList;
    private User currentUser;
    private ListView taskListView;
    private ArrayAdapter taskArrayAdapter;

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

        this.taskListView = view.findViewById(R.id.tasks_list_view);
        this.taskListView = (ListView) view.findViewById(R.id.tasks_list_view);

        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.getTasks(this.currentUser.uid, new TaskListCallback() {
            @Override
            public void getTaskList(boolean isValid, List<Task> tasks) {
                if (isValid) {
                    for (int i = 0; i < tasks.size(); i++) {
                        Log.d("yathavan", tasks.get(i).toString());
                    }

                    taskArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, tasks) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view1 = super.getView(position, convertView, parent);

                            TextView text1 = (TextView) view1.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view1.findViewById(android.R.id.text2);

                            text1.setText(tasks.get(position).getName());

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                            Date date = new Date(Long.parseLong(tasks.get(position).getDue_date())*1000L);
                            text2.setText(simpleDateFormat.format(date));

                            return view1;
                        }
                    };

                    taskListView.setAdapter(taskArrayAdapter);
                }
            }
        });

        ExtendedFloatingActionButton add_task_button = getView().findViewById(R.id.add_task_button);

        add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.homescreen_transaction_frame, new TaskBuilder(currentUser)).commit();
            }
        });
    }
}