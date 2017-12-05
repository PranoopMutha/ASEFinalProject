package com.knowledge.quickassistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLogin extends AppCompatActivity {
    TextView signup;
    EditText User, Pass;
    FirebaseDatabase database;
    DatabaseReference LoginRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        database = FirebaseDatabase.getInstance();
        LoginRef = database.getReference("Users");
        signup = (TextView)findViewById(R.id.LoginContent_SignUpButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserRegistration.class);
                startActivity(intent);
            }
        });
        User = (EditText)findViewById(R.id.LoginContent_UserNameTextField);
        Pass = (EditText)findViewById(R.id.LoginContent_PasswordTextField);
    }

    public void Login(View view) {
        final String UserName = User.getText().toString();
        final String password = Pass.getText().toString();

        if (UserName.isEmpty()) {
            User.setError("Username cannot be blank");
        } else if (password.isEmpty()) {
            Pass.setError("Password cannot be blank");
        } else {
            LoginRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(UserName)) {
                        DataSnapshot pass = dataSnapshot.child(UserName).child("Password");
                        String UserN = dataSnapshot.child(UserName).child("UserName").getValue().toString();
                        if (password.equals(pass.getValue().toString())) {
                            Pass.setError(null);
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserLogin.this,Home.class);
                            intent.putExtra("UserName",UserN);
                            startActivity(intent);
                        } else {
                            Pass.setError("Enter Correct Password");
                        }
                    } else {
                        User.setError("Enter Correct Username");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void forgotPassword(View view){
        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }

    public void engineerRegistration(View view){
        Intent intent = new Intent(UserLogin.this,EngineerLogin.class);
        startActivity(intent);
    }

}
