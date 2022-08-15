package yath.sfwrtech4sa3.remindme;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;
import java.net.URL;

interface ViewsCallback {
    void isUserExist(boolean doesExist, User user);
}

public class HomeScreen extends AppCompatActivity {
    private final DatabaseHelper databaseHelper = new DatabaseHelper();
    private User currentUser;
    private Fragment taskListFragment;
    private ShapeableImageView avatar_img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.avatar_img_view = findViewById(R.id.home_avatar_img);

        NavigationBar navigationBar = new NavigationBar();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_transaction_frame, navigationBar);
        fragmentTransaction.commit();


        databaseHelper.getUser(getIntent().getStringExtra("UID"), new ViewsCallback() {
            @Override
            public void isUserExist(boolean doesExist, User user) {
                if (doesExist) {
                    try {
                        InputStream inputStream = (InputStream) new URL(user.profile_pic_uri).getContent();
                        avatar_img_view.setImageDrawable(Drawable.createFromStream(inputStream, user.display_name + " profile picture"));
                    } catch (Exception exception) {
                        Log.d("yathavan", "Couldn't set avatar image: " + exception.getMessage());
                    }
                }
            }
        });
    }
}

