package com.knowledge.quickassistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EngineerHome extends AppCompatActivity {

    String Name,UserN;
    TextView UserName;
    EditText First, Last, Email, Phone, Address, City, State, Zipcode, Experience, BaseFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_home);

        Firebase.setAndroidContext(this);

        Intent EditProfileIntent = getIntent();
        Name = EditProfileIntent.getStringExtra("UserName");
        UserName = (TextView) findViewById(R.id.Engineer_Profile_Registration_UserName);

        //UserName.setText(Name);

        First = (EditText) findViewById(R.id.Engineer_Profile_Registration_FirstName_TextField);
        Last = (EditText) findViewById(R.id.Engineer_Profile_Registration_LastName_TextField);
        Email = (EditText) findViewById(R.id.Engineer_Profile_Registration_EmailId_TextField);
        Phone = (EditText) findViewById(R.id.Engineer_Profile_Registration_Contact_TextField);
        Address = (EditText) findViewById(R.id.LoginContent_AddressTextField);
        City = (EditText) findViewById(R.id.LoginContent_CityTextField);
        State = (EditText) findViewById(R.id.LoginContent_StateTextField);
        Zipcode = (EditText) findViewById(R.id.LoginContent_PincodeTextField);
        Experience = (EditText) findViewById(R.id.LoginContent_ExperienceTextField);
        BaseFare = (EditText) findViewById(R.id.LoginContent_BaseFareTextField);



        Firebase.setAndroidContext(this);

        final Firebase GetProfileRef = new Firebase("https://quickassistance-16201.firebaseio.com/Engineers");

        GetProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                First.setText(dataSnapshot.child(Name).child("FirstName").getValue().toString());
                Last.setText(dataSnapshot.child(Name).child("LastName").getValue().toString());
                Email.setText(dataSnapshot.child(Name).child("EmailId").getValue().toString());
                Phone.setText(dataSnapshot.child(Name).child("Phone").getValue().toString());
                Address.setText(dataSnapshot.child(Name).child("Address").getValue().toString());
                City.setText(dataSnapshot.child(Name).child("City").getValue().toString());
                State.setText(dataSnapshot.child(Name).child("State").getValue().toString());
                Zipcode.setText(dataSnapshot.child(Name).child("Zipcode").getValue().toString());
//                Experience.setText(dataSnapshot.child(Name).child("Experience").getValue().toString());
//                BaseFare.setText(dataSnapshot.child(Name).child("BaseFare").getValue().toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void UpdateProfile(View view){

        final Firebase EditProfileRef = new Firebase("https://quickassistance-16201.firebaseio.com/Engineers");

        final String FirstName = ((EditText)findViewById(R.id.Engineer_Profile_Registration_FirstName_TextField)).getText().toString();
        final String LastName = ((EditText)findViewById(R.id.Engineer_Profile_Registration_LastName_TextField)).getText().toString();
        final String EmaiId = ((EditText)findViewById(R.id.Engineer_Profile_Registration_EmailId_TextField)).getText().toString();
        final String Contact = ((EditText)findViewById(R.id.Engineer_Profile_Registration_Contact_TextField)).getText().toString();
        final String address = ((EditText) findViewById(R.id.LoginContent_AddressTextField)).getText().toString();
        final String city = ((EditText) findViewById(R.id.LoginContent_CityTextField)).getText().toString();
        final String state = ((EditText) findViewById(R.id.LoginContent_StateTextField)).getText().toString();
        final String zipcode = ((EditText) findViewById(R.id.LoginContent_PincodeTextField)).getText().toString();
        final String experience = ((EditText) findViewById(R.id.LoginContent_ExperienceTextField)).getText().toString();
        final String baseFare = ((EditText) findViewById(R.id.LoginContent_BaseFareTextField)).getText().toString();


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
                    EditUserRef.child("Address").setValue(address);
                    EditUserRef.child("City").setValue(city);
                    EditUserRef.child("State").setValue(state);
                    EditUserRef.child("Zipcode").setValue(zipcode);
                    EditUserRef.child("Experience").setValue(experience);
                    EditUserRef.child("BaseFare").setValue(baseFare);

                    Toast.makeText(getApplicationContext(), "Profile has been successfully updated", Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(EngineerHome.this, Home.class);
                    //startActivity(intent);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void ChangePassword(View view){
        Intent intent = new Intent(EngineerHome.this,EngineerPasswordChange.class);
        intent.putExtra("UserName",Name);
        startActivity(intent);
    }


}
