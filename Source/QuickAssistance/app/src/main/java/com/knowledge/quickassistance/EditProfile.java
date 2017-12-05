package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    private StorageReference mStorageRef;
    String Name;
    TextView UserName;
    EditText First, Last, Email, Phone;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference GetProfileRef,EditProfileRef;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
        Intent EditProfileIntent = getIntent();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Name = EditProfileIntent.getStringExtra("UserName");
        UserName = (TextView) findViewById(R.id.EditProfileUserName);
        UserName.setText(Name);
        imageView = (ImageView)findViewById(R.id.imageView7);
        First = (EditText) findViewById(R.id.EditProfileFirstName);
        Last = (EditText) findViewById(R.id.EditProfile_LastName);
        Email = (EditText) findViewById(R.id.ForgotPasswordUserName);
        Phone = (EditText) findViewById(R.id.EditProfileContact);
        StorageReference childref = mStorageRef.child("images/rivers.jpg");
        childref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext()).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(getApplicationContext(),"There is some problem encountered during image loading",Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        GetProfileRef = firebaseDatabase.getReference("Users");

        GetProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                First.setText(dataSnapshot.child(Name).child("FirstName").getValue().toString());
                Last.setText(dataSnapshot.child(Name).child("LastName").getValue().toString());
                Email.setText(dataSnapshot.child(Name).child("EmailId").getValue().toString());
                Phone.setText(dataSnapshot.child(Name).child("Phone").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void ChangePassword(View view){
        Intent intent = new Intent(EditProfile.this,ChangePassword.class);
        intent.putExtra("UserName",Name);
        startActivity(intent);
    }

    public void UpdateProfile(View view){

        EditProfileRef = firebaseDatabase.getReference("Users");
        final String FirstName = ((EditText)findViewById(R.id.EditProfileFirstName)).getText().toString();
        final String LastName = ((EditText)findViewById(R.id.EditProfile_LastName)).getText().toString();
        final String EmaiId = ((EditText)findViewById(R.id.ForgotPasswordUserName)).getText().toString();
        final String Contact = ((EditText)findViewById(R.id.EditProfileContact)).getText().toString();
        final DatabaseReference EditUserRef = EditProfileRef.child(Name);

        EditProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(Name)) {
                    EditUserRef.child("UserName").setValue(Name);
                    EditUserRef.child("FirstName").setValue(FirstName);
                    EditUserRef.child("LastName").setValue(LastName);
                    EditUserRef.child("EmailId").setValue(EmaiId);
                    EditUserRef.child("Phone").setValue(Contact);

                    Toast.makeText(getApplicationContext(), "Profile has been successfully updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditProfile.this, Home.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 00 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            imageView.setImageURI(uri);
            StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getfile();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
    }
    public void getfile()
    {
        StorageReference childref = mStorageRef.child("images/rivers.jpg");
        childref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(getApplicationContext()).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(getApplicationContext(),"Didn't get file",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void change(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,00);
    }
}
