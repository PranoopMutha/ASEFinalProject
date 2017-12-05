package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EngineerLogin extends AppCompatActivity {
    EditText User, Pass;
    FirebaseDatabase database;
    DatabaseReference LoginRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_login);
        User = (EditText) findViewById(R.id.LoginContent_UserNameTextField);
        Pass = (EditText) findViewById(R.id.LoginContent_PasswordTextField);
        database = FirebaseDatabase.getInstance();
        LoginRef = database.getReference("Engineers");
    }
    public void Login(View view){
        final String UserName = ((EditText)findViewById(R.id.LoginContent_UserNameTextField)).getText().toString();
        final String password = ((EditText)findViewById(R.id.LoginContent_PasswordTextField)).getText().toString();

        if (UserName.isEmpty()){
            User.setError("Username cannot be blank");
        }
        else if (password.isEmpty()){
            Pass.setError("Password cannot be blank");
        }
        else {
            LoginRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(UserName)) {
                        DataSnapshot pass = dataSnapshot.child(UserName).child("Password");
                        String UserN = dataSnapshot.child(UserName).child("UserName").getValue().toString();
                        if (password.equals(pass.getValue().toString())) {
                            Pass.setError(null);
                            Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EngineerLogin.this, EngineerHome.class);
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
    public void registration(View view){
        Intent intent = new Intent(EngineerLogin.this, EngineerProfileRegistration.class);
        startActivity(intent);
    }

    public void forgotPassword(View view)
    {
        Intent intent = new Intent(this,EngineerForgotPassword.class);
        startActivity(intent);
    }
}
