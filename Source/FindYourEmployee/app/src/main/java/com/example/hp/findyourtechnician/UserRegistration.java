package com.example.hp.findyourtechnician;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration extends AppCompatActivity {

    EditText Firstname, Lastname, Emailid, contact, Username, password, Confirmpassword;
    Firebase RegistrationRef;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        Firebase.setAndroidContext(this);

        RegistrationRef = new Firebase("https://findyourtechnician.firebaseio.com/");

        Firstname = (EditText)findViewById(R.id.LoginContent_FirstNameTextField);
        Lastname = (EditText)findViewById(R.id.LoginContent_LastNameTextField);
        Emailid = (EditText)findViewById(R.id.LoginContent_EmailidTextField);
        contact = (EditText)findViewById(R.id.LoginContent_ContactTextField);
        Username = (EditText)findViewById(R.id.LoginContent_UserNameTextField);
        password = (EditText)findViewById(R.id.LoginContent_PasswordTextField);
        Confirmpassword = (EditText)findViewById(R.id.LoginContent_ConfirmPasswordTextField);
    }

    public void Register(View view) {


        String FirstName = ((EditText) findViewById(R.id.LoginContent_FirstNameTextField)).getText().toString();
        String LastName = ((EditText) findViewById(R.id.LoginContent_LastNameTextField)).getText().toString();
        String EmailId = ((EditText) findViewById(R.id.LoginContent_EmailidTextField)).getText().toString();
        String Contact = ((EditText) findViewById(R.id.LoginContent_ContactTextField)).getText().toString();
        String UserName = ((EditText) findViewById(R.id.LoginContent_UserNameTextField)).getText().toString();
        String Password = ((EditText) findViewById(R.id.LoginContent_PasswordTextField)).getText().toString();
        String ConfirmPassword = ((EditText) findViewById(R.id.LoginContent_ConfirmPasswordTextField)).getText().toString();

        //Firebase RegistrationRef = new Firebase("https://findyourtechnician.firebaseio.com/");
        Firebase RegistrationChildRef = RegistrationRef.child("Users").child(UserName);

        if (validateUser(UserName)) {
            Username.setError("User name is already in use");
            Username.requestFocus();
        } else if (!validateEmail(EmailId)) {
            Emailid.setError("Enter Valid Email");
            Emailid.requestFocus();
        } else if ((Password.length() < 8) || (Password.length() > 15)) {
            password.setError("Password should be between 8 and 15 characters in length");
            password.requestFocus();
        }  else if (!Password.equals(ConfirmPassword)) {
            Confirmpassword.setError("Password and confirm password should be same");
            password.requestFocus();
        } else {
            RegistrationChildRef.child("FirstName").setValue(FirstName);
            RegistrationChildRef.child("LastName").setValue(LastName);
            RegistrationChildRef.child("EmailId").setValue(EmailId);
            RegistrationChildRef.child("Phone").setValue(Contact);
            RegistrationChildRef.child("UserName").setValue(UserName);
            RegistrationChildRef.child("Password").setValue(Password);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(UserRegistration.this, UserLogin.class);
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
    public boolean validateUser(final String User){

        RegistrationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
