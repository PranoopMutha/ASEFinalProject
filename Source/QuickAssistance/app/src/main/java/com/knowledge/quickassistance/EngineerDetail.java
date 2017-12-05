package com.knowledge.quickassistance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EngineerDetail extends AppCompatActivity {

    TextView name, category, experience, fare, contact, email;
    String userName, engineerName, loginName, engineerUserName, categorySelected, subCategorySelected, location;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_detail);

        Intent intent = getIntent();
        engineerName = intent.getStringExtra("name");
        engineerUserName = intent.getStringExtra("engineerUserName");
        loginName = intent.getStringExtra("UserName");
        categorySelected = intent.getStringExtra("category");
        subCategorySelected = intent.getStringExtra("SubCategory");
        location = intent.getStringExtra("location");


        category = (TextView) findViewById(R.id.ContentTechnicianDetails_TechnicianCategory);
        contact = (TextView) findViewById(R.id.ContentEngineerDetails_EngineerPhoneNumber);
        email = (TextView) findViewById(R.id.ContentEngineerDetails_EngineerEmail);
        experience = (TextView) findViewById(R.id.ContentEngineerDetails_EngineerExperience);
        fare = (TextView) findViewById(R.id.ContentEngineerDetails_EngineerCost);
        name = (TextView) findViewById(R.id.ContentEngineerDetails_TechnicianName);

        name.setText(engineerName);

        Firebase.setAndroidContext(this);
        final Firebase technicianRef = new Firebase("https://quickassistance-16201.firebaseio.com/Engineers");
        technicianRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String TechExperience = dataSnapshot.child(engineerUserName).child("experience").getValue().toString();
                String TechPhone = dataSnapshot.child(engineerUserName).child("Phone").getValue().toString();
                String TechEmail = dataSnapshot.child(engineerUserName).child("EmailId").getValue().toString();
                String TechCategory = dataSnapshot.child(engineerUserName).child("Expertise").getValue().toString();
                //String TechFare = dataSnapshot.child(engineerUserName).child("BaseFare").getValue().toString();
                userName = dataSnapshot.child(engineerUserName).child("UserName").getValue().toString();

                category.setText(TechCategory);
                contact.setText(TechPhone);
                email.setText(TechEmail);
                experience.setText(TechExperience);
                //fare.setText(TechFare);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void Feedback(View view){
        Intent intent = new Intent(EngineerDetail.this, Feedback.class);
        intent.putExtra("UserName", userName);
        intent.putExtra("LUserName", loginName);
        intent.putExtra("name", engineerName);
        intent.putExtra("category", categorySelected);
        intent.putExtra("SubCategory", subCategorySelected);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void callEngineer(View view) {

        String Phone = ((TextView) findViewById(R.id.ContentEngineerDetails_EngineerPhoneNumber)).getText().toString();
        /*Intent CallTech = new Intent(Intent.ACTION_CALL, Uri.parse(Phone));
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
        startActivity(CallTech);*/

        String Message = "Vinay Jois viewed your profile and is interested in recruiting you. Contact number: 2406651339";

        smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Phone, null, Message, null, null);

        Toast.makeText(getApplicationContext(),"User has been notified through sms", Toast.LENGTH_SHORT).show();
    }

}
