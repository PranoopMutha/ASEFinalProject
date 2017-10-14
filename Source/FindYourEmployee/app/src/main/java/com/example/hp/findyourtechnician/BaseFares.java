package com.example.hp.findyourtechnician;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class BaseFares extends AppCompatActivity {

    String FirstName, LastName, EmailId, Contact, Address, City, State, Zipcode, Experience, Category, UserName, Password, Country;
    Firebase RegistrationRef;
    String SCategoryF1, SCategoryF2, SCategoryF3, SCategoryF4, SCategoryF5;
    TextView SubCategory1,SubCategory2,SubCategory3,SubCategory4;
    EditText SubCategory5;
    String[] SubCategory = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fares);

        Intent intent = getIntent();
        FirstName = intent.getStringExtra("FirstName");
        LastName = intent.getStringExtra("LastName");
        EmailId = intent.getStringExtra("EmailId");
        Contact = intent.getStringExtra("Phone");
        Address = intent.getStringExtra("Address");
        State = intent.getStringExtra("State");
        City = intent.getStringExtra("City");
        Zipcode = intent.getStringExtra("Zipcode");
        UserName = intent.getStringExtra("UserName");
        Password = intent.getStringExtra("Password");
        Experience = intent.getStringExtra("Experience");
        Country = intent.getStringExtra("Country");
        Category = intent.getStringExtra("Category");


        SubCategory1 = (TextView)findViewById(R.id.SubCategory1);
        SubCategory2 = (TextView)findViewById(R.id.SubCategory2);
        SubCategory3 = (TextView)findViewById(R.id.SubCategory3);
        SubCategory4 = (TextView)findViewById(R.id.SubCategory4);

        SubCategory5 = (EditText) findViewById(R.id.SubCategoryFareMinimum);


        Resources res = getResources();

        if(Category.equalsIgnoreCase("Carpenter")) {
            SubCategory = res.getStringArray(R.array.Carpenter_SubCategory);
        }
        if(Category.equalsIgnoreCase("Electrician")) {
            SubCategory = res.getStringArray(R.array.Electrician_SubCategory);
        }
        if(Category.equalsIgnoreCase("Mechanic")) {
            SubCategory = res.getStringArray(R.array.Mechanic_SubCategory);
        }
        if(Category.equalsIgnoreCase("Plumber")) {
            SubCategory = res.getStringArray(R.array.Plumber_SubCategory);
        }
        if(Category.equalsIgnoreCase("Painter")) {
            SubCategory = res.getStringArray(R.array.Painter_SubCategory);
        }
        SubCategory1.setText(SubCategory[0]);
        SubCategory2.setText(SubCategory[1]);
        SubCategory3.setText(SubCategory[2]);
        SubCategory4.setText(SubCategory[3]);


    }

    public void Register(View view){

        Firebase.setAndroidContext(this);
        RegistrationRef = new Firebase("https://findyourtechnician.firebaseio.com/");
        Firebase RegistrationChildRef = RegistrationRef.child("Technicians").child(UserName);

        SCategoryF1 = ((EditText) findViewById(R.id.SubCategoryFare1)).getText().toString();
        SCategoryF2 = ((EditText) findViewById(R.id.SubCategoryFare2)).getText().toString();
        SCategoryF3 = ((EditText) findViewById(R.id.SubCategoryFare3)).getText().toString();
        SCategoryF4 = ((EditText) findViewById(R.id.SubCategoryFare4)).getText().toString();
        SCategoryF5 = ((EditText) findViewById(R.id.SubCategoryFareMinimum)).getText().toString();

        if (SCategoryF5.isEmpty()){
            SubCategory5.setError("This field is mandatory");
        }
        else {
            RegistrationChildRef.child("FirstName").setValue(FirstName);
            RegistrationChildRef.child("LastName").setValue(LastName);
            RegistrationChildRef.child("EmailId").setValue(EmailId);
            RegistrationChildRef.child("Phone").setValue(Contact);
            RegistrationChildRef.child("Address").setValue(Address);
            RegistrationChildRef.child("State").setValue(State);
            RegistrationChildRef.child("City").setValue(City);
            RegistrationChildRef.child("Zipcode").setValue(Zipcode);
            RegistrationChildRef.child("UserName").setValue(UserName);
            RegistrationChildRef.child("Password").setValue(Password);
            RegistrationChildRef.child("Experience").setValue(Experience);
            RegistrationChildRef.child("Country").setValue(Country);
            RegistrationChildRef.child("Expertise").setValue(Category);
            RegistrationChildRef.child("Rating").child("Count").setValue(0);
            RegistrationChildRef.child("Rating").child("Rating").setValue(0);
            RegistrationChildRef.child("SubCategory").child("Other").setValue(SCategoryF5);

            if (!SCategoryF1.isEmpty()){
                RegistrationChildRef.child("SubCategory").child(SubCategory[0]).setValue(SCategoryF1);
            }
            if (!SCategoryF2.isEmpty()){
                RegistrationChildRef.child("SubCategory").child(SubCategory[1]).setValue(SCategoryF2);
            }
            if (!SCategoryF3.isEmpty()){
                RegistrationChildRef.child("SubCategory").child(SubCategory[2]).setValue(SCategoryF3);
            }
            if (!SCategoryF4.isEmpty()){
                RegistrationChildRef.child("SubCategory").child(SubCategory[3]).setValue(SCategoryF4);
            }

            Intent intent = new Intent(BaseFares.this, SuccessScreen.class);
            startActivity(intent);
        }

    }

}
