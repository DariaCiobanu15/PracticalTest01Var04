package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    private EditText studentNameEditText;
    private EditText groupEditText;
    private Button okButton;
    private Button cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.buttonOk) {
                setResult(RESULT_OK, null);
                finish();
            } else if (view.getId() == R.id.buttonCancel) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        studentNameEditText = (EditText)findViewById(R.id.editTextName);
        groupEditText = (EditText)findViewById(R.id.editTextGroup);

        okButton = (Button)findViewById(R.id.buttonOk);
        cancelButton = (Button)findViewById(R.id.buttonCancel);

        okButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.STUDENT_NAME)) {
            studentNameEditText.setText(intent.getStringExtra(Constants.STUDENT_NAME));

        }
        if (intent != null && intent.getExtras().containsKey(Constants.GROUP)) {
            groupEditText.setText(intent.getStringExtra(Constants.GROUP));
        }

    }
}