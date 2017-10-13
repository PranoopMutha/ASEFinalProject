package com.example.hp.findyourtechnician;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TechnicianProfileRegistration extends AppCompatActivity {

    Spinner ActivityCategory_Spinner;
    ArrayAdapter<CharSequence> ActivityCategory_adapter;
    EditText Firstname, Lastname, Emailid, contact, Username, password, Confirmpassword;
    EditText address, state, zipcode, city, experience, country;
    Spinner expertise;
    Firebase RegistrationRef;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_profile_registration);
        ActivityCategory_Spinner = (Spinner)findViewById(R.id.LoginContent_Expertise);
        ActivityCategory_adapter = ArrayAdapter.createFromResource(this,R.array.technicians,android.R.layout.simple_spinner_item);
        ActivityCategory_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivityCategory_Spinner.setAdapter(ActivityCategory_adapter);

        Firebase.setAndroidContext(this);
        RegistrationRef = new Firebase("https://findyourtechnician.firebaseio.com/");

        Firstname = (EditText)findViewById(R.id.Technician_Profile_Registration_FirstName_TextField);
        Lastname = (EditText)findViewById(R.id.Technician_Profile_Registration_LastName_TextField);
        Emailid = (EditText)findViewById(R.id.Technician_Profile_Registration_EmailId_TextField);
        contact = (EditText)findViewById(R.id.Technician_Profile_Registration_Contact_TextField);
        Username = (EditText)findViewById(R.id.Technician_Profile_Registration_UserName_TextField);
        password = (EditText)findViewById(R.id.Technician_Profile_Registration_PassWord_TextField);
        Confirmpassword = (EditText)findViewById(R.id.Technician_Profile_Registration_ConfirmPassword_TextField);
        address = (EditText)findViewById(R.id.LoginContent_AddressTextField);
        state = (EditText)findViewById(R.id.LoginContent_StateTextField);
        city = (EditText)findViewById(R.id.LoginContent_CityTextField);
        zipcode = (EditText)findViewById(R.id.LoginContent_PincodeTextField);
        experience = (EditText)findViewById(R.id.LoginContent_ExperienceTextField);
        country = (EditText)findViewById(R.id.LoginContent_BaseFareTextField);
        expertise = (Spinner) findViewById(R.id.LoginContent_Expertise);
    }

    public void TechnicianRegistration(View view) {

        String FirstName = ((EditText) findViewById(R.id.Technician_Profile_Registration_FirstName_TextField)).getText().toString();
        String LastName = ((EditText) findViewById(R.id.Technician_Profile_Registration_LastName_TextField)).getText().toString();
        String EmailId = ((EditText) findViewById(R.id.Technician_Profile_Registration_EmailId_TextField)).getText().toString();
        String Contact = ((EditText) findViewById(R.id.Technician_Profile_Registration_Contact_TextField)).getText().toString();
        String UserName = ((EditText) findViewById(R.id.Technician_Profile_Registration_UserName_TextField)).getText().toString();
        String Password = ((EditText) findViewById(R.id.Technician_Profile_Registration_PassWord_TextField)).getText().toString();
        String ConfirmPassword = ((EditText) findViewById(R.id.Technician_Profile_Registration_ConfirmPassword_TextField)).getText().toString();
        String Address = ((EditText)findViewById(R.id.LoginContent_AddressTextField)).getText().toString();
        String State = ((EditText)findViewById(R.id.LoginContent_StateTextField)).getText().toString();
        String City = ((EditText)findViewById(R.id.LoginContent_CityTextField)).getText().toString();
        String Zipcode = ((EditText)findViewById(R.id.LoginContent_PincodeTextField)).getText().toString();
        String Experience = ((EditText)findViewById(R.id.LoginContent_ExperienceTextField)).getText().toString();
        String Country = ((EditText)findViewById(R.id.LoginContent_BaseFareTextField)).getText().toString();
        String Expertise = expertise.getSelectedItem().toString();

        //Firebase RegistrationRef = new Firebase("https://findyourtechnician.firebaseio.com/");
        Firebase RegistrationChildRef = RegistrationRef.child("Technicians").child(UserName);

        if (!validateEmail(EmailId)) {
            Emailid.setError("Enter Valid Email");
            Emailid.requestFocus();
        } else if (Contact.length()!=10) {
            contact.setError("Enter a valid Phone Number");
            contact.requestFocus();
        } else if (Address.isEmpty()) {
            address.setError("Address cannot be empty");
            address.requestFocus();
        } else if (State.isEmpty()) {
            state.setError("State cannot be empty");
            state.requestFocus();
        } else if (City.isEmpty()) {
            city.setError("City cannot be empty");
            city.requestFocus();
        } else if (Zipcode.isEmpty()) {
            zipcode.setError("Pincode cannot be empty");
            zipcode.requestFocus();
        } else if (Experience.isEmpty()) {
            experience.setError("Experience field cannot be empty");
            experience.requestFocus();
        } else if ((Password.length() < 8) || (Password.length() > 15)) {
            password.setError("Password should be between 8 and 15 characters in length");
            password.requestFocus();
        } else if (!Password.equals(ConfirmPassword)) {
            Confirmpassword.setError("Password and confirm password should be same");
            password.requestFocus();
        } else {
            Intent intent = new Intent(TechnicianProfileRegistration.this, BaseFares.class);
            intent.putExtra("FirstName", FirstName);
            intent.putExtra("LastName", LastName);
            intent.putExtra("EmailId", EmailId);
            intent.putExtra("Phone", Contact);
            intent.putExtra("Address", Address);
            intent.putExtra("State", State);
            intent.putExtra("City", City);
            intent.putExtra("Zipcode", Zipcode);
            intent.putExtra("UserName", UserName);
            intent.putExtra("Password", Password);
            intent.putExtra("Experience", Experience);
            intent.putExtra("Country", Country);
            intent.putExtra("Category", Expertise);
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
                if (dataSnapshot.hasChild(User)) {
                    flag = true;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return flag;
    }

}