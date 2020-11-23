package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView log;
    private EditText ename,eemail,erollno, epassword;
    private RadioGroup role;
    private RadioButton chkbtn;
    String role1;
    private Button reg;
    private FirebaseAuth mAuth;
    private ProgressBar progessbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        log = (TextView)findViewById(R.id.rlogin);
        log.setOnClickListener(this);

        reg = (Button)findViewById(R.id.register);
        reg.setOnClickListener(this);

        ename = (EditText)findViewById(R.id.name1);
        eemail = (EditText)findViewById(R.id.email1);
        erollno = (EditText)findViewById(R.id.rollno1);
        epassword = (EditText)findViewById(R.id.password1);
        progessbar = (ProgressBar)findViewById(R.id.ProgressBar1);
        role = (RadioGroup)findViewById(R.id.role1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlogin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.register:
                register_user();
                break;
            default:
                break;
        }
    }


    private void register_user(){
        Log.d("Register","Yes");
        String email = eemail.getText().toString().trim();
        String name = ename.getText().toString().trim();
        String roll = erollno.getText().toString().trim();
        String pass = epassword.getText().toString().trim();
        chkbtn = (RadioButton) findViewById(role.getCheckedRadioButtonId());
        role1 = chkbtn.getText().toString().trim();
        if(name.isEmpty()){
            ename.setError("Name is required");
            ename.requestFocus();
            return;
        }
        if(roll.isEmpty()){
            erollno.setError("Roll No. is required");
            erollno.requestFocus();
            return;
        }
        if(email.isEmpty()){
            eemail.setError("Email is required");
            eemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eemail.setError("Provide valid email address");
            eemail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            epassword.setError("Password is required");
            epassword.requestFocus();
            return;
        }
        if(pass.length()<6){
            epassword.setError("Min length should be 6 characters");
            epassword.requestFocus();
            return;
        }
        Log.d("email",email);
        Log.d("Password",pass);

        mAuth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(name,email,roll,role1);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,"User has been registered successfully!",Toast.LENGTH_SHORT).show();
                                    if(role1.equalsIgnoreCase("Student") )
                                        startActivity(new Intent(RegisterActivity.this,Student.class));
                                    else if(role1.equalsIgnoreCase("Teacher"))
                                        startActivity(new Intent(RegisterActivity.this,Admin.class));
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }else {
                        Log.d("Database","No");
                        Toast.makeText(RegisterActivity.this,"Failed to register!!",Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}