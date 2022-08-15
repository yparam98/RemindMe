package yath.sfwrtech4sa3.remindme;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;

public class NavigationBar extends Fragment {
    private NavigationBarView navigationBarView;

    public NavigationBar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.navigationBarView = view.findViewById(R.id.app_navigation_bar);

        this.navigationBarView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.view_tasks_menu_item:
                        Log.d("yathavan", "view tasks");
                        fragment = new TaskListView();
                        break;
                    case R.id.add_task_menu_item:
                        Log.d("yathavan", "add task");
                        fragment = new TaskBuilder();
                        break;
                    case R.id.view_ally_menu_item:
                        fragment = new ViewAllies();
                        break;
                    case R.id.add_ally_menu_item:
                        fragment = new AddAlly();
                        break;
                    case R.id.remind_ally_menu_item:
                        fragment = new TaskListView();
                        break;
                    default:
                        fragment = new TaskListView();
                }

                FragmentManager fragmentManager = getParentFragmentManager();
                Log.d("yathavan", fragmentManager.getFragments().toString());
                fragmentManager.beginTransaction().replace(R.id.homescreen_transaction_frame, fragment).commit();
            }
        });

    }
}