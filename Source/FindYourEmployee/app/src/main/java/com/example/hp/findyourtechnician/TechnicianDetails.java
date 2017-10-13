package com.example.hp.findyourtechnician;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class TechnicianDetails extends AppCompatActivity {

    TextView Name, Category, Experience, Fare, Contact, Email;
    String Uname, TechnicianName, LoginName, TUserName, CategorySelected, SubCategorySelected, Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_details);

        Intent intent = getIntent();
        TechnicianName = intent.getStringExtra("Name");
        TUserName = intent.getStringExtra("TUserName");
        LoginName = intent.getStringExtra("UserName");
        CategorySelected = intent.getStringExtra("Category");
        SubCategorySelected = intent.getStringExtra("SubCategory");
        Location = intent.getStringExtra("Location");


        Category = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianCategory);
        Contact = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianPhoneNumber);
        Email = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianEmail);
        Experience = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianExperience);
        Fare = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianCost);
        Name = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianName);

        Name.setText(TechnicianName);

        Firebase.setAndroidContext(this);
        final Firebase TechnicianRef = new Firebase("https://findyourtechnician.firebaseio.com/Technicians");
        TechnicianRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*for (DataSnapshot NameList : dataSnapshot.getChildren()) {
                    String TechFirstName = NameList.child("FirstName").getValue().toString();
                    String TechLastName = NameList.child("LastName").getValue().toString();
                    String TechFullName = TechFirstName + " " + TechLastName;
                    if (TechFullName.contentEquals(TechnicianName)) {*/
                        String TechExperience = dataSnapshot.child(TUserName).child("Experience").getValue().toString();
                        String TechPhone = dataSnapshot.child(TUserName).child("Phone").getValue().toString();
                        String TechEmail = dataSnapshot.child(TUserName).child("EmailId").getValue().toString();
                        String TechCategory = dataSnapshot.child(TUserName).child("Expertise").getValue().toString();
                        String TechFare = dataSnapshot.child(TUserName).child("BaseFare").getValue().toString();
                        Uname = dataSnapshot.child(TUserName).child("UserName").getValue().toString();

                        Category.setText(TechCategory);
                        Contact.setText(TechPhone);
                        Email.setText(TechEmail);
                        Experience.setText(TechExperience);
                        Fare.setText(TechFare);

                        /*break;

                    }
                }*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void Feedback(View view){
        Intent intent = new Intent(TechnicianDetails.this, Feedback.class);
        intent.putExtra("UserName",Uname);
        intent.putExtra("LUserName",LoginName);
        intent.putExtra("Name", TechnicianName);
        intent.putExtra("Category", CategorySelected);
        intent.putExtra("SubCategory", SubCategorySelected);
        intent.putExtra("Location", Location);
        startActivity(intent);
    }

    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void CallTechnician(View view) {

        String Phone = ((TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianPhoneNumber)).getText().toString();

        Intent CallTech = new Intent(Intent.ACTION_CALL, Uri.parse(Phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(CallTech);

    }

}