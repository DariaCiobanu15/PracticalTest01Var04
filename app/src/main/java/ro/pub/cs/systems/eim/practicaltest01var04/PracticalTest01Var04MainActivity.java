package ro.pub.cs.systems.eim.practicaltest01var04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    private Button navigateToSecondaryActivityButton;
    private Button displayInformationButton;
    private EditText StudentNameEditText;
    private EditText GroupEditText;
    private CheckBox StudentCheckBox;
    private CheckBox GroupCheckBox;

    private TextView informationTextView;

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
            } else if (view.getId() == R.id.buttonNavigateToSecondaryActivity) {
                Toast.makeText(getApplicationContext(), "Navigate to secondary activity", Toast.LENGTH_SHORT).show();
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



    }
}