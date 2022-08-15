package yath.sfwrtech4sa3.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private Intent googleSignInIntent;
    private Intent homeScreenIntent;
    private DatabaseHelper databaseHelper;
    private TaskListView taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        this.databaseHelper = new DatabaseHelper();

//        this.taskList = (TaskListView) getSupportFragmentManager().findFragmentById(R.id.tasks_list);

        this.googleSignInIntent = new Intent(this, GoogleSignIn.class);
        this.homeScreenIntent = new Intent(this, HomeScreen.class);

        /* "context": As the name suggests, it's the context of the
         * current state of the application/object. It lets newly-created
         * objects understand what has been going on. Typically you call it
         * to get information regarding another part of your program (activity
         * and package/application). */

        // image button to facilitate google sign in
        ImageButton googleSignInButton = findViewById(R.id.google_signin_image_button);
//        Button myTasksButton = findViewById(R.id.my_tasks_button);

        // click listener and handler for google sign in button
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(googleSignInIntent, 27);
            }
        });

//        myTasksButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (resultCode == RESULT_OK && requestCode == 27) {
            if (data.hasExtra("UID")) {
                this.databaseHelper.getUser(data.getStringExtra("UID"), new ViewsCallback() {
                    @Override
                    public void isUserExist(boolean doesExist, User user) {
                        if (!doesExist) {
                            databaseHelper.addAccount(new User(
                                    data.getExtras().getString("DISPLAY_NAME"),
                                    data.getExtras().getString("UID"),
                                    data.getExtras().getString("PICTURE_URI"),
                                    data.getExtras().getString("EMAIL")
                            ));
                        }
                        finish();
                        homeScreenIntent.putExtra("UID", data.getExtras().getString("UID"));
                        startActivity(homeScreenIntent);
                    }
                });
            }
        }
    }
}