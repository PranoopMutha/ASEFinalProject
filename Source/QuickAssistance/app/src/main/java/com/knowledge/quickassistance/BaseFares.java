package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseFares extends AppCompatActivity {
    String FirstName, LastName, EmailId, Contact, Address, City, State, Zipcode, Experience, Category, UserName, Password, Country;
    FirebaseDatabase database;
    DatabaseReference RegistrationRef,RegistrationChildRef;
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
        Experience = intent.getStringExtra("experience");
        Country = intent.getStringExtra("Country");
        Category = intent.getStringExtra("category");
        SubCategory1 = (TextView)findViewById(R.id.SubCategoryOne);
        SubCategory2 = (TextView)findViewById(R.id.SubCategoryTwo);
        SubCategory3 = (TextView)findViewById(R.id.SubCategoryThree);
        SubCategory4 = (TextView)findViewById(R.id.SubCategoryFour);
        SubCategory5 = (EditText) findViewById(R.id.SubCategoryFareMinimum);

        Resources res = getResources();
        if(Category.equalsIgnoreCase("Front End Developer")) {
            SubCategory = res.getStringArray(R.array.FrontEnd_SubCategory);
        }
        if(Category.equalsIgnoreCase("Back End Developer")) {
            SubCategory = res.getStringArray(R.array.BackEnd_SubCategory);
        }
        if(Category.equalsIgnoreCase("Full Stack Developer")) {
            SubCategory = res.getStringArray(R.array.FullStack_SubCategory);
        }
        if(Category.equalsIgnoreCase("Test Engineer")) {
            SubCategory = res.getStringArray(R.array.Tester_SubCategory);
        }
        SubCategory1.setText(SubCategory[0]);
        SubCategory2.setText(SubCategory[1]);
        SubCategory3.setText(SubCategory[2]);
        SubCategory4.setText(SubCategory[3]);
    }
    public void Register(View view){

        database = FirebaseDatabase.getInstance();
        RegistrationRef = database.getReference();
        RegistrationChildRef = RegistrationRef.child("Engineers").child(UserName);

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
            RegistrationChildRef.child("experience").setValue(Experience);
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
            Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BaseFares.this, SuccessScreen.class);
            startActivity(intent);
        }

    }
}
