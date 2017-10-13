package com.example.hp.findyourtechnician;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EditProfilePage extends AppCompatActivity {

    String Name,UserN;
    TextView UserName;
    EditText First, Last, Email, Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);

        Intent EditProfileIntent = getIntent();
        Name = EditProfileIntent.getStringExtra("UserName");
        UserName = (TextView) findViewById(R.id.EditProfile_UserName);
        UserName.setText(Name);

        First = (EditText) findViewById(R.id.EditProfile_FirstName);
        Last = (EditText) findViewById(R.id.EditProfile_LastName);
        Email = (EditText) findViewById(R.id.ForgotPassword_UserName);
        Phone = (EditText) findViewById(R.id.EditProfile_Contact);


        Firebase.setAndroidContext(this);

        final Firebase GetProfileRef = new Firebase("https://findyourtechnician.firebaseio.com/Users");

        GetProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                First.setText(dataSnapshot.child(Name).child("FirstName").getValue().toString());
                Last.setText(dataSnapshot.child(Name).child("LastName").getValue().toString());
                Email.setText(dataSnapshot.child(Name).child("EmailId").getValue().toString());
                Phone.setText(dataSnapshot.child(Name).child("Phone").getValue().toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void ChangePassword(View view){
        Intent intent = new Intent(EditProfilePage.this,ChangePassword.class);
        intent.putExtra("UserName",Name);
        startActivity(intent);
    }

    public void UpdateProfile(View view){

        final Firebase EditProfileRef = new Firebase("https://findyourtechnician.firebaseio.com/Users");

        final String FirstName = ((EditText)findViewById(R.id.EditProfile_FirstName)).getText().toString();
        final String LastName = ((EditText)findViewById(R.id.EditProfile_LastName)).getText().toString();
        final String EmaiId = ((EditText)findViewById(R.id.ForgotPassword_UserName)).getText().toString();
        final String Contact = ((EditText)findViewById(R.id.EditProfile_Contact)).getText().toString();

        final Firebase EditUserRef = EditProfileRef.child(Name);

        EditProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(Name)) {
                    EditUserRef.child("UserName").setValue(Name);
                    EditUserRef.child("FirstName").setValue(FirstName);
                    EditUserRef.child("LastName").setValue(LastName);
                    EditUserRef.child("EmailId").setValue(EmaiId);
                    EditUserRef.child("Phone").setValue(Contact);

                    Toast.makeText(getApplicationContext(), "Profile has been successfully updated", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(EditProfilePage.this, Home.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

}