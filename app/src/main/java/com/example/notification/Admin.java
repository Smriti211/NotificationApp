package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    // RecyclerView recyclerView;
    FloatingActionButton floating_button;
    private Button logout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // recyclerView = findViewById(R.id.recyclerView);
        floating_button = findViewById(R.id.floatingActionButton);

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, AddActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Admin.this,MainActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String subject = bundle.getString("subject");
            String branch = bundle.getString("branch");
            String details = bundle.getString("details");
            String date = bundle.getString("date");
            String prior = bundle.getString("priority");
            TextView sub = findViewById(R.id.show_subject);
            TextView brn = findViewById(R.id.show_branch);
            TextView det = findViewById(R.id.show_details);
            TextView d = findViewById(R.id.show_date);
            TextView p = findViewById(R.id.show_priority);
            sub.setText(subject);
            brn.setText(branch);
            det.setText(details);
            d.setText(date);
            p.setText(prior);
        }
    }
}