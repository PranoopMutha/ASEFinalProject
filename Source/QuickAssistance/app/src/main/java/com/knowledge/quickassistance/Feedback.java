package com.knowledge.quickassistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Feedback extends AppCompatActivity {

    RatingBar ratingBar;
    EditText Comment;
    String ActualRating,UserName, FullName, Feedback, PresentRating, LoginName, AvgRating, CategorySelected, SubCategorySelected,Location;
    Double AverageRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Firebase.setAndroidContext(this);

        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        FullName = intent.getStringExtra("Name");
        LoginName = intent.getStringExtra("LUserName");
        CategorySelected = intent.getStringExtra("Category");
        SubCategorySelected = intent.getStringExtra("SubCategory");
        Location = intent.getStringExtra("Location");

        ratingBar = (RatingBar) findViewById(R.id.Feedback_rating);
        Comment = (EditText) findViewById(R.id.Feedback_Comment);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ActualRating = String.valueOf(rating);
            }
        });

    }

    public void SubmitFeedback(View view){

        final Firebase FeedbackRef = new Firebase("https://quickassistance-16201.firebaseio.com/Engineers");
        final Firebase CommentsRef = FeedbackRef.child(UserName).child("Comments");
        final Firebase RatingRef = FeedbackRef.child(UserName).child("Rating");

        Feedback = Comment.getText().toString();

        FeedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                PresentRating = "0.0";

                PresentRating = dataSnapshot.child(UserName).child("Rating").child("Rating").getValue().toString();
                Double Count = Double.parseDouble(dataSnapshot.child(UserName).child("Rating").child("Count").getValue().toString());

                AverageRating = ((Count * Double.parseDouble(PresentRating)) + Double.parseDouble(ActualRating))/ ( Count + 1 );

                String Comment = LoginName + ":";
                Double dou = new Double(Double.parseDouble(ActualRating));

                FeedbackRef.child(UserName).child("Rating").child("Rating").setValue(AverageRating);
                CommentsRef.child(Comment).setValue(Feedback);

                Intent intent = new Intent(Feedback.this, Home.class);
                intent.putExtra("Name", FullName);
                intent.putExtra("Category", CategorySelected);
                intent.putExtra("SubCategory", SubCategorySelected);
                intent.putExtra("Location", Location);
                intent.putExtra("UserName", LoginName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
