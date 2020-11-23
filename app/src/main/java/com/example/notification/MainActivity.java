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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView reg;
    private EditText eemail,epassword;
    private Button login;
    private RadioGroup role;
    private RadioButton chkbtn;
    String role1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg = (TextView)findViewById(R.id.lreg);
        reg.setOnClickListener(this);

        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);

        eemail = (EditText)findViewById(R.id.email);
        epassword = (EditText)findViewById(R.id.password);
        role = (RadioGroup)findViewById(R.id.role);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lreg:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.login:
                login_user();
                mAuth.signOut();
                break;
            default:
                break;
        }
    }

    private void login_user(){
        String email = eemail.getText().toString().trim();
        String pass = epassword.getText().toString().trim();
        chkbtn = (RadioButton) findViewById(role.getCheckedRadioButtonId());
        role1 = chkbtn.getText().toString().trim();
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
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("Name",email);
                    if(role1.equalsIgnoreCase("Student")) {
                        startActivity(new Intent(MainActivity.this,Student.class));
                    }else {
                        startActivity(new Intent(MainActivity.this, Admin.class));
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Failed to Login! Check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}