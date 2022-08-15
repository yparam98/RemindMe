package yath.sfwrtech4sa3.remindme;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
        Map<String, Object> user = new HashMap<>();
        user.put("display_name", incoming_user.display_name);
        user.put("uid", incoming_user.uid);
        user.put("profile_pic_uri", incoming_user.profile_pic_uri);
        user.put("email", incoming_user.email);

        Map<String, Object> taskList = new HashMap<>();
        taskList.put("user_id", incoming_user.uid);
        taskList.put("tasks", new ArrayList<yath.sfwrtech4sa3.remindme.Task>());

        database.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("yathavan", "User created with ID " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("yathavan", "Error adding user: " + e.getMessage());
                    }
                });

        database.collection("taskList")
                .add(taskList)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("yathavan", "TaskList created with ID " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("yathavan", "Error adding taskList: " + e.getMessage());
                    }
                });
    }

    public void addTask(yath.sfwrtech4sa3.remindme.Task incoming_task) {
        Map<String, Object> task = new HashMap<>();
        task.put("task_id", incoming_task.task_id);
        task.put("name", incoming_task.name);
        task.put("description", incoming_task.description);
        task.put("created_date", incoming_task.created_date);
        task.put("due_date", incoming_task.due_date);
        task.put("user_id", incoming_task.userID);

        database.collection("tasks")
                .add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("yathavan", "Task created with ID " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("yathavan", "Error adding task: " + e.getMessage());
                    }
                });
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

    public void getTaskList(String incoming_uid, TaskListCallback taskListCallback) {

    }
}
