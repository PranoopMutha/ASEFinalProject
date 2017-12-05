package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/24/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration extends AppCompatActivity {
    EditText Firstname, Lastname, Emailid, contact, Username, password, Confirmpassword;
    FirebaseDatabase database;
    Firebase RegistrationRef;
    DatabaseReference databaseReference, registrationChildRef;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
        Firstname = (EditText)findViewById(R.id.LoginContent_FirstNameTextField);
        Lastname = (EditText)findViewById(R.id.LoginContent_LastNameTextField);
        Emailid = (EditText)findViewById(R.id.LoginContent_EmailidTextField);
        contact = (EditText)findViewById(R.id.LoginContent_ContactTextField);
        Username = (EditText)findViewById(R.id.LoginContent_UserNameTextField);
        password = (EditText)findViewById(R.id.LoginContent_PasswordTextField);
        Confirmpassword = (EditText)findViewById(R.id.LoginContent_ConfirmPasswordTextField);
    }
    public void Register(View view)
    {
        String FirstName =Firstname.getText().toString();
        String LastName = Lastname.getText().toString();
        String EmailId = Emailid.getText().toString();
        String Contact = contact.getText().toString();
        final String UserName = Username.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = Confirmpassword.getText().toString();
        Firebase RegistrationChildRef = RegistrationRef.child("Users").child(UserName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UserName)){
                    flag = true;
                }
                else{
                    flag = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (flag) {
            Username.setError("User name is already in use");
            Username.requestFocus();
        } else if (!validateEmail(EmailId)) {
            Emailid.setError("Enter Valid email");
            Emailid.requestFocus();
        } else if ((Password.length() < 6) || (Password.length() > 15)) {
            password.setError("Password should be between 6 and 15 characters in length");
            password.requestFocus();
        }  else if (!Password.equals(ConfirmPassword)) {
            Confirmpassword.setError("Password and confirm password should be same");
            password.requestFocus();
        }
        else {
            registrationChildRef = database.getReference("Users").child(UserName);
            registrationChildRef.child("FirstName").setValue(FirstName);
            registrationChildRef.child("LastName").setValue(LastName);
            registrationChildRef.child("EmailId").setValue(EmailId);
            registrationChildRef.child("Phone").setValue(Contact);
            registrationChildRef.child("UserName").setValue(UserName);
            registrationChildRef.child("Password").setValue(Password);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserRegistration.this,UserLogin.class);
            startActivity(intent);
        }
    }
    public boolean validateEmail(String Email)
    {
        String EmailPattern  = "^[A-Za-z][A-Za-z0-9]*([._-]?[A-Za-z0-9]+)@[A-Za-z]+.[A-Za-z]{0,3}$";

        Pattern pattern = Pattern.compile(EmailPattern);
        Matcher matcher = pattern.matcher(Email);

        return matcher.matches();
    }

    /*public boolean validateUser(final String User){

        RegistrationRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(User)){
                    flag = true;
                }
                else{
                    flag = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return flag;
    }*/
}
