package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    //RecyclerView recyclerView;
    FloatingActionButton floating_button;
    final ArrayList<String> list=new ArrayList<>();
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        final ListView listView=(ListView)findViewById(R.id.listview);
        floating_button = findViewById(R.id.floatingActionButton);

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, AddActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        /*logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Admin.this, MainActivity.class));
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
        }*/
        Log.d("details","true");
        FirebaseDatabase.getInstance().getReference()
                .child("Event")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String s = "";
                            s = snapshot.child("subject").getValue().toString()+"\n" +
                                    snapshot.child("date").getValue().toString()+"\n" +
                                    snapshot.child("priority").getValue().toString()+"\n"+
                                    snapshot.child("branch").getValue().toString()+"\n"+
                                    snapshot.child("details").getValue().toString();
                            Log.d("Data",s);
                            list.add(s);
//                            sub.setText(snapshot.child("subject").getValue().toString());
//                            brn.setText(snapshot.child("branch").getValue().toString());
//                            det.setText(snapshot.child("details").getValue().toString());
//                            d.setText(snapshot.child("date").getValue().toString());
//                            p.setText(snapshot.child("priority").getValue().toString());

                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Admin.this, android.R
                                .layout.simple_list_item_activated_1,list);
                        listView.setAdapter(arrayAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}