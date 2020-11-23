package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText subject_text,date_text,details_text,others_text;

    Button add_event;
    RadioGroup branch, priority;
    RadioButton branch_checked,priority_checked;
    String branch_input, priority_input,subject_input,date_input,details_input,others_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        subject_text = (EditText)findViewById(R.id.subject);
        date_text = (EditText) findViewById(R.id.date);
        details_text = (EditText) findViewById(R.id.details);
        others_text = (EditText) findViewById(R.id.others);
        add_event = (Button)findViewById(R.id.add_button);
        branch = (RadioGroup)findViewById(R.id.radio) ;
        priority= (RadioGroup)findViewById(R.id.radio2);




        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch_checked = (RadioButton)findViewById(branch.getCheckedRadioButtonId());
                branch_input = branch_checked.getText().toString();

                priority_checked = (RadioButton)findViewById(priority.getCheckedRadioButtonId());
                priority_input = priority_checked.getText().toString().trim();
                subject_input = subject_text.getText().toString();
                date_input = date_text.getText().toString();
                details_input = details_text.getText().toString();
                others_input = others_text.getText().toString();
                Toast.makeText(AddActivity.this,"added",Toast.LENGTH_SHORT).show();
                Log.d("Subject",subject_input);

                Bundle bundle = new Bundle();
                bundle.putString("subject", subject_input);
                bundle.putString("branch", branch_input);
                bundle.putString("details", details_input);
                bundle.putString("date", date_input);
                bundle.putString("priority", priority_input);
                Intent intent = new Intent(AddActivity.this, Admin.class);
                intent.putExtras(bundle);
                startActivity(intent);
               // display.setText(subject_input+" "+date_input);
            }
        });

    }



}