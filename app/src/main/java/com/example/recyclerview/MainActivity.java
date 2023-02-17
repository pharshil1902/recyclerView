package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnView;
    EditText name, email, age;
    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btninsert);
        btnView = findViewById(R.id.btnview);
        name = findViewById(R.id.edtname);
        email = findViewById(R.id.edtemail);
        age = findViewById(R.id.edtage);
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Userlist.class));
                finish();
            }
        });
    }
    private void InsertData() {
        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String userage = age.getText().toString();
        String id = databaseUsers.push().getKey();

        User user = new User(username,useremail,userage);
        databaseUsers.child("users").child(id).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Details Inserted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}