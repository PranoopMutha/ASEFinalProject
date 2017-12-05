package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EngineerProfileRegistration extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference RegistrationRef,RegistrationChildRef;
    Spinner ActivityCategory_Spinner;
    ArrayAdapter<CharSequence> ActivityCategory_adapter;
    EditText Firstname, Lastname, Emailid, contact, Username, password, Confirmpassword;
    EditText address, state, zipcode, city, experience, country;
    Spinner expertise;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_profile_registration);
        ActivityCategory_Spinner = (Spinner)findViewById(R.id.LoginContent_Expertise);
        ActivityCategory_adapter = ArrayAdapter.createFromResource(this,R.array.engineers,android.R.layout.simple_spinner_item);
        ActivityCategory_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivityCategory_Spinner.setAdapter(ActivityCategory_adapter);
        database = FirebaseDatabase.getInstance();
        RegistrationRef = database.getReference();
        Firstname = (EditText)findViewById(R.id.Engineer_Profile_Registration_FirstName_TextField);
        Lastname = (EditText)findViewById(R.id.Engineer_Profile_Registration_LastName_TextField);
        Emailid = (EditText)findViewById(R.id.Engineer_Profile_Registration_EmailId_TextField);
        contact = (EditText)findViewById(R.id.Engineer_Profile_Registration_Contact_TextField);
        Username = (EditText)findViewById(R.id.Engineer_Profile_Registration_UserName_TextField);
        password = (EditText)findViewById(R.id.Engineer_Profile_Registration_PassWord_TextField);
        Confirmpassword = (EditText)findViewById(R.id.Engineer_Profile_Registration_ConfirmPassword_TextField);
        address = (EditText)findViewById(R.id.LoginContent_AddressTextField);
        state = (EditText)findViewById(R.id.LoginContent_StateTextField);
        city = (EditText)findViewById(R.id.LoginContent_CityTextField);
        zipcode = (EditText)findViewById(R.id.LoginContent_PincodeTextField);
        experience = (EditText)findViewById(R.id.LoginContent_ExperienceTextField);
        country = (EditText)findViewById(R.id.LoginContent_BaseFareTextField);
        expertise = (Spinner) findViewById(R.id.LoginContent_Expertise);
    }
    public void engineerRegistration(View view) {

        String FirstName = ((EditText) findViewById(R.id.Engineer_Profile_Registration_FirstName_TextField)).getText().toString();
        String LastName = ((EditText) findViewById(R.id.Engineer_Profile_Registration_LastName_TextField)).getText().toString();
        String EmailId = ((EditText) findViewById(R.id.Engineer_Profile_Registration_EmailId_TextField)).getText().toString();
        String Contact = ((EditText) findViewById(R.id.Engineer_Profile_Registration_Contact_TextField)).getText().toString();
        String UserName = ((EditText) findViewById(R.id.Engineer_Profile_Registration_UserName_TextField)).getText().toString();
        String Password = ((EditText) findViewById(R.id.Engineer_Profile_Registration_PassWord_TextField)).getText().toString();
        String ConfirmPassword = ((EditText) findViewById(R.id.Engineer_Profile_Registration_ConfirmPassword_TextField)).getText().toString();
        String Address = ((EditText)findViewById(R.id.LoginContent_AddressTextField)).getText().toString();
        String State = ((EditText)findViewById(R.id.LoginContent_StateTextField)).getText().toString();
        String City = ((EditText)findViewById(R.id.LoginContent_CityTextField)).getText().toString();
        String Zipcode = ((EditText)findViewById(R.id.LoginContent_PincodeTextField)).getText().toString();
        String Experience = ((EditText)findViewById(R.id.LoginContent_ExperienceTextField)).getText().toString();
        String Country = ((EditText)findViewById(R.id.LoginContent_BaseFareTextField)).getText().toString();
        String Expertise = expertise.getSelectedItem().toString();

        //Firebase RegistrationRef = new Firebase("https://findyourtechnician.firebaseio.com/");
        RegistrationChildRef = RegistrationRef.child("Engineers").child(UserName);

        if (!validateEmail(EmailId)) {
            Emailid.setError("Enter Valid email");
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
            experience.setError("experience field cannot be empty");
            experience.requestFocus();
        } else if ((Password.length() < 8) || (Password.length() > 15)) {
            password.setError("Password should be between 8 and 15 characters in length");
            password.requestFocus();
        } else if (!Password.equals(ConfirmPassword)) {
            Confirmpassword.setError("Password and confirm password should be same");
            password.requestFocus();
        } else {
            Intent intent = new Intent(EngineerProfileRegistration.this, BaseFares.class);
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
            intent.putExtra("experience", Experience);
            intent.putExtra("Country", Country);
            intent.putExtra("category", Expertise);
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
}