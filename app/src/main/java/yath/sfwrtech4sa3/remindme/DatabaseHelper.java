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

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseHelper {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    DatabaseHelper() { }

    public void addAccount(User incoming_user) {
        database.collection("users").document(incoming_user.uid).set(incoming_user);
        database.collection("taskLists").document("t-"+incoming_user.uid).set(new TaskList(incoming_user.uid));
    }

    public void addTask(yath.sfwrtech4sa3.remindme.Task incoming_task, String incoming_uid) {
        database.collection("taskLists").document("t-"+incoming_uid)
                .update("tasks", FieldValue.arrayUnion(incoming_task));
    }

    public void addAlly(String incoming_user_id, String incoming_ally_id) {
        Ally ally = new Ally(incoming_user_id, incoming_ally_id);
        database.collection("allies").document(ally.ally_record_id).set(ally);
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

    public void getTasks(String incoming_uid, TaskListCallback taskListCallback) {
        this.database.collection("taskLists")
                .whereEqualTo("userID", incoming_uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            try {
                                taskListCallback.getTaskList(true, task.getResult().toObjects(TaskList.class).get(0).tasks);
                            } catch (Exception exception) {
                                Log.d("yathavan", "error getting tasklist: " + exception.getMessage());
                            }
                        } else {
                            taskListCallback.getTaskList(false, null);
                        }

                    }
                });
    }

    public void getAllies(String incoming_user_id, AllyCallback allyCallback) {
        this.database.collection("allies")
                .whereEqualTo("user_id", incoming_user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            try {
                                allyCallback.getAllyRecord(true, task.getResult().toObjects(Ally.class).get(0));
                            } catch (Exception exception) {
                                Log.d("yathavan", "Error getting Ally: " + exception.getMessage());
                                allyCallback.getAllyRecord(false, null);
                            }
                        }
                    }
                });

    }
}
