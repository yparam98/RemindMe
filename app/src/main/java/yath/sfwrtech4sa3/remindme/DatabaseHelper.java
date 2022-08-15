package yath.sfwrtech4sa3.remindme;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseHelper {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    DatabaseHelper() { }

    public void addAccount(User incoming_user) {
//        Map<String, Object> user = new HashMap<>();
//        user.put("display_name", incoming_user.display_name);
//        user.put("uid", incoming_user.uid);
//        user.put("profile_pic_uri", incoming_user.profile_pic_uri);
//        user.put("email", incoming_user.email);

        Map<String, Object> taskList = new HashMap<>();
        taskList.put("user_id", incoming_user.uid);
        taskList.put("tasks", new ArrayList<yath.sfwrtech4sa3.remindme.Task>());

        database.collection("users").document(incoming_user.uid).set(incoming_user);
        database.collection("taskLists").document("t-"+incoming_user.uid).set(new TaskList(incoming_user.uid));
    }

    public void addTask(yath.sfwrtech4sa3.remindme.Task incoming_task, String incoming_uid) {
        database.collection("taskLists").document("t-"+incoming_uid)
                .update("tasks", FieldValue.arrayUnion(incoming_task));
    }

    public void getUser(String incoming_uid, ViewsCallback viewsCallback) {
        this.database.collection("users")
                .whereEqualTo("uid", incoming_uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            try {
                                viewsCallback.isUserExist(true, task.getResult().toObjects(User.class).get(0));
                            } catch (Exception exception) {
                                viewsCallback.isUserExist(false, null);
                            }
                        } else {
                            viewsCallback.isUserExist(false, null);
                        }
                    }
                });
    }
}
