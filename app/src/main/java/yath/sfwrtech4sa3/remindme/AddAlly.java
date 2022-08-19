package yath.sfwrtech4sa3.remindme;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

interface AllyCallback {
    void getAllyRecord(boolean isExist, List<Ally> allies);
}

public class AddAlly extends DialogFragment {
    private User current_user;
    private DatabaseHelper databaseHelper;

    public AddAlly() { }

    public AddAlly(User user) {
        this.current_user = user;
        this.databaseHelper = new DatabaseHelper();
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

        TextInputEditText ally_uid_input = view.findViewById(R.id.ally_add_text_input);
        MaterialTextView current_uid_text_view = view.findViewById(R.id.uid_output);
        MaterialButton uid_ok_button = view.findViewById(R.id.ally_add_ok_button);

        current_uid_text_view.setText(current_user.uid);

        uid_ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.addAlly(ally_uid_input.getText().toString(), current_user.uid);
                ally_uid_input.setText("");
            }
        });
    }
}