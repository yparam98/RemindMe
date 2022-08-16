package yath.sfwrtech4sa3.remindme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class TaskBuilder extends DialogFragment {

    private TextInputEditText taskNameInput;
    private TextInputEditText dueDatePicker;
    private Button ok_button;
    private User currentUser;

   public TaskBuilder() { }

    public TaskBuilder(User user) {
       this.currentUser = user;
       Log.d("yathavan", "user reached taskbuilder");
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_builder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.taskNameInput = view.findViewById(R.id.task_name_input);
        this.dueDatePicker = view.findViewById(R.id.task_due_date_picker_text);

        this.ok_button = view.findViewById(R.id.task_ok_button);

        this.dueDatePicker.setInputType(InputType.TYPE_NULL);
        this.dueDatePicker.setKeyListener(null);
        this.dueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                MaterialDatePicker<Long> picker = builder.build();
                picker.show(getParentFragmentManager(), "DATE_PICKER");
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(selection);
                        dueDatePicker.setText(calendar.getTime().toString());
                    }
                });
            }
        });

        this.ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper();
//                String incoming_name, String incoming_desc, String incoming_created_date, String incoming_due_date, String incoming_uid
                Task task = new Task(taskNameInput.getText().toString(), dueDatePicker.getText().toString());
                databaseHelper.addTask(task, currentUser.uid);
            }
        });

    }
}