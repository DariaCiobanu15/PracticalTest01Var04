package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    private Button navigateToSecondaryActivityButton;
    private Button displayInformationButton;
    private EditText StudentNameEditText;
    private EditText GroupEditText;
    private CheckBox StudentCheckBox;
    private CheckBox GroupCheckBox;

    private TextView informationTextView;

    private boolean serviceStaus = false;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.buttonDisplayInformation) {
                if (StudentCheckBox.isChecked()) {
                    if (StudentNameEditText.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Student name is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        informationTextView.setText(StudentNameEditText.getText().toString());
                    }
                }
                if (GroupCheckBox.isChecked()) {
                    if (GroupEditText.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Group is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        informationTextView.append(GroupEditText.getText().toString());
                    }
                }
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                intent.putExtra(Constants.STUDENT_NAME, StudentNameEditText.getText().toString());
                intent.putExtra(Constants.GROUP, GroupEditText.getText().toString());
                getApplicationContext().startService(intent);
                serviceStaus = true;

            } else if (view.getId() == R.id.buttonNavigateToSecondaryActivity) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
                intent.putExtra(Constants.STUDENT_NAME, StudentNameEditText.getText().toString());
                intent.putExtra(Constants.GROUP, GroupEditText.getText().toString());
                activityResultLauncher.launch(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.buttonNavigateToSecondaryActivity);
        displayInformationButton = (Button)findViewById(R.id.buttonDisplayInformation);
        StudentNameEditText = (EditText)findViewById(R.id.editTextStudentName);
        GroupEditText = (EditText)findViewById(R.id.editTextGroup);
        StudentCheckBox = (CheckBox)findViewById(R.id.checkBoxStudentName);
        GroupCheckBox = (CheckBox)findViewById(R.id.checkBoxGroup);

        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);
        displayInformationButton.setOnClickListener(buttonClickListener);

        informationTextView = (TextView)findViewById(R.id.textView);

        activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Toast.makeText(this, "The activity returned with result OK " +
                        result.getResultCode(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The activity returned with result CANCELED " +
                        result.getResultCode(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.STUDENT_NAME, StudentNameEditText.getText().toString());
        savedInstanceState.putString(Constants.GROUP, GroupEditText.getText().toString());
        savedInstanceState.putBoolean(Constants.STUDENT_CHECKBOX, StudentCheckBox.isChecked());
        savedInstanceState.putBoolean(Constants.GROUP_CHECKBOX, GroupCheckBox.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.STUDENT_NAME)) {
            StudentNameEditText.setText(savedInstanceState.getString(Constants.STUDENT_NAME));
        } else {
            StudentNameEditText.setText("");
        }
        if (savedInstanceState.containsKey(Constants.GROUP)) {
            GroupEditText.setText(savedInstanceState.getString(Constants.GROUP));
        } else {
            GroupEditText.setText("");
        }
        if (savedInstanceState.containsKey(Constants.STUDENT_CHECKBOX)) {
            StudentCheckBox.setChecked(savedInstanceState.getBoolean(Constants.STUDENT_CHECKBOX));
        } else {
            StudentCheckBox.setChecked(false);
        }
        if (savedInstanceState.containsKey(Constants.GROUP_CHECKBOX)) {
            GroupCheckBox.setChecked(savedInstanceState.getBoolean(Constants.GROUP_CHECKBOX));
        } else {
            GroupCheckBox.setChecked(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private IntentFilter intentFilter = new IntentFilter();
    {
        intentFilter.addAction(Constants.ACTION_STRING);
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.PROCESSING_THREAD_TAG, "onReceive() callback method has been invoked");
            Log.d(Constants.PROCESSING_THREAD_TAG, intent.getStringExtra(Constants.STUDENT_NAME));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }

    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}