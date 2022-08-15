package yath.sfwrtech4sa3.remindme;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.io.InputStream;
import java.net.URL;

public class NavigationDrawer extends Fragment {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ShapeableImageView drawer_avatar_image_view;
    private TextView drawer_name_text_view;
    private View headerView;
    private Menu drawer_menu;


    public NavigationDrawer() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("yathavan", "navigation drawer view created");

        this.drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        this.navigationView = (NavigationView) view.findViewById(R.id.navigation_view);

        this.headerView = navigationView.inflateHeaderView(R.layout.header_navigation_drawer_layout);
        this.drawer_menu = navigationView.getMenu();

        this.drawer_name_text_view = (TextView) this.headerView.findViewById(R.id.navigation_drawer_textview);
        this.drawer_avatar_image_view = (ShapeableImageView) this.headerView.findViewById(R.id.navigation_drawer_avatar_img);

        this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.view_tasks_menu_item:
                        Log.d("yathavan", "view tasks");
                        return false;
                    case R.id.add_task_menu_item:
                        Log.d("yathavan", "add tasks");
                        return false;
                    case R.id.view_ally_menu_item:
                        Log.d("yathavan", "view allies");
                        return false;
                    case R.id.add_ally_menu_item:
                        Log.d("yathavan", "add ally");
                        return false;
                    case R.id.remind_ally_menu_item:
                        Log.d("yathavan", "remind ally");
                        return false;
                    default:
                        NavigationDrawer.super.onOptionsItemSelected(item);
                }
                return false;
            }
        });
    }

    public void setMeta(String profile_picture_uri, String display_name) {
        Log.d("yathavan", "setting drawer meta");

        if (this.drawer_name_text_view != null && this.drawer_avatar_image_view != null) {
            try {
                this.drawer_name_text_view.setText(display_name);
                InputStream inputStream = (InputStream) new URL(profile_picture_uri).getContent();
                this.drawer_avatar_image_view.setImageDrawable(Drawable.createFromStream(inputStream, display_name + " profile picture"));
            } catch (Exception e) {
                Log.d("yathavan", "error setting profile picture: " + e.getMessage());
            }
        } else {
            Log.d("yathavan", "bitch it's null");
        }
    }
}