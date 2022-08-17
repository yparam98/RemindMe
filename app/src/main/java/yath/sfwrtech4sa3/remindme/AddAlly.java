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
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

interface AllyCallback {
    void getAllyRecord(boolean isExist, Ally ally);
}

public class AddAlly extends DialogFragment {
    private User current_user;
    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    private PreviewView previewView;
//    private Lifecycle lifecycle = getLifecycle();

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
        this.previewView = view.findViewById(R.id.qr_camera_preview_pane);

        try {
            InputStream inputStream = (InputStream) new URL("https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=" + current_user.uid + "&choe=UTF-8").getContent();
            user_qr_img.setImageDrawable(Drawable.createFromStream(inputStream, current_user.display_name + " qr code"));
        } catch (Exception exception) {
            Log.d("yathavan", "error creating qr code");
        }

//        getActivity().requestPermissions(new String[] {Manifest.permission.CAMERA}, 73);
        this.cameraProviderListenableFuture = ProcessCameraProvider.getInstance(getContext());

        this.cameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider processCameraProvider = cameraProviderListenableFuture.get();
                bindPreview(processCameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this.getContext()));
    }

    private void bindPreview(@NonNull ProcessCameraProvider incoming_processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        preview.setSurfaceProvider(this.previewView.getSurfaceProvider());
        Camera camera = incoming_processCameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview);
    }
}