package yath.sfwrtech4sa3.remindme;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

interface ViewsCallback {
    void isUserExist(boolean doesExist, User user);
}

public class HomeScreen extends AppCompatActivity {
    private final DatabaseHelper databaseHelper = new DatabaseHelper();
    private User currentUser;
    private Fragment taskListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        NavigationDrawer navigationDrawer = new NavigationDrawer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homescreen_transaction_frame, navigationDrawer);
        fragmentTransaction.commit();
        Log.d("yathavan", "fragment committed");

        databaseHelper.getUser(getIntent().getStringExtra("UID"), new ViewsCallback() {
            @Override
            public void isUserExist(boolean doesExist, User user) {
                if (doesExist) {
                    Log.d("yathavan", user.toString());
                    navigationDrawer.setMeta(user.profile_pic_uri, user.display_name);
                }
            }
        });
    }
}

