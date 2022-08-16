package yath.sfwrtech4sa3.remindme;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;
import java.net.URL;

public class AddAlly extends DialogFragment {
    User current_user;

    public AddAlly() { }

    public AddAlly(User user) {
        this.current_user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ally, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShapeableImageView user_qr_img = view.findViewById(R.id.user_qr_code);
        try {
            InputStream inputStream = (InputStream) new URL("https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=" + current_user.uid + "&choe=UTF-8").getContent();
            user_qr_img.setImageDrawable(Drawable.createFromStream(inputStream, current_user.display_name + " qr code"));
        } catch (Exception exception) {
            Log.d("yathavan", "error creating qr code");
        }
    }
}