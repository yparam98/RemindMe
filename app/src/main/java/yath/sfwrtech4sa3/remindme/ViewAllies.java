package yath.sfwrtech4sa3.remindme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewAllies extends Fragment {

    public ViewAllies() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView allyViewRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_view_allies, container, false);
        String current_user_uid = getArguments().getString("UID");
        final DatabaseHelper databaseHelper = new DatabaseHelper();

        List<String> uids = new ArrayList<>();
        List<String> display_names = new ArrayList<>();
        List<String> profile_picture_uris = new ArrayList<>();

        // getting allies from DB, figure out how to do it async then reload adapter


        databaseHelper.getAllies(current_user_uid, new AllyCallback() {
            @Override
            public void getAllyRecord(boolean isExist, List<Ally> allies) {
                if (isExist) {
                    for (Ally ally : allies) {
                        databaseHelper.getUser(ally.ally_id, new ViewsCallback() {
                            @Override
                            public void isUserExist(boolean doesExist, User user) {
                                if (doesExist) {
                                    display_names.add(user.getDisplay_name());
                                    profile_picture_uris.add(user.getProfile_pic_uri());
                                }
                            }
                        });
                    }

                    ViewAlliesAdapter viewAlliesAdapter = new ViewAlliesAdapter(uids.toArray(new String[0]), display_names.toArray(new String[0]), profile_picture_uris.toArray(new String[0]));
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
                    allyViewRecycler.setAdapter(viewAlliesAdapter);
                    allyViewRecycler.setLayoutManager(gridLayoutManager);
                    allyViewRecycler.notify();
                }
            }
        });

        ViewAlliesAdapter viewAlliesAdapter = new ViewAlliesAdapter(uids.toArray(new String[0]), display_names.toArray(new String[0]), profile_picture_uris.toArray(new String[0]));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        allyViewRecycler.setAdapter(viewAlliesAdapter);
        allyViewRecycler.setLayoutManager(gridLayoutManager);

        return allyViewRecycler;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}