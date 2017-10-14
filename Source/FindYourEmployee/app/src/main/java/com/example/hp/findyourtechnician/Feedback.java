package com.example.hp.findyourtechnician;

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

        final Firebase FeedbackRef = new Firebase("https://findyourtechnician.firebaseio.com/Technicians");
        final Firebase CommentsRef = FeedbackRef.child(UserName).child("Comments");
        final Firebase RatingRef = FeedbackRef.child(UserName).child("Rating");

        Feedback = Comment.getText().toString();

        FeedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //PresentRating = dataSnapshot.child(UserName).child("Rating").getValue().toString();
                PresentRating = "0.0";

                PresentRating = dataSnapshot.child(UserName).child("Rating").child("Rating").getValue().toString();
                Double Count = Double.parseDouble(dataSnapshot.child(UserName).child("Rating").child("Count").getValue().toString());

                AverageRating = ((Count * Double.parseDouble(PresentRating)) + Double.parseDouble(ActualRating))/ ( Count + 1 );

                //String a="0.5", b="1.5", c="2.5", d="3.5", e="4.5";

                //FeedbackRef.child(UserName).child("Rating").child("0").setValue(1);
                //FeedbackRef.child(UserName).child("Rating").child(a).setValue(2);
                //FeedbackRef.child(UserName).child("Rating").child("1").setValue(4);
                //FeedbackRef.child(UserName).child("Rating").child(b).setValue(2);
                //FeedbackRef.child(UserName).child("Rating").child("2").setValue(2);
                //FeedbackRef.child(UserName).child("Rating").child(c).setValue(7);
                //FeedbackRef.child(UserName).child("Rating").child("3").setValue(10);
                //FeedbackRef.child(UserName).child("Rating").child(d).setValue(7);
                //FeedbackRef.child(UserName).child("Rating").child("4").setValue(8);
                //FeedbackRef.child(UserName).child("Rating").child(e).setValue(3);
                //FeedbackRef.child(UserName).child("Rating").child("5").setValue(6);
                /*
                Double AcRating = Double.parseDouble(ActualRating);
                if (AcRating > 0 && AcRating <= 0.5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("0").getValue().toString();
                }
                else if (AcRating > 0.5 && AcRating <= 1.5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("1").getValue().toString();
                }
                else if (AcRating > 1.5 && AcRating <= 2.5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("2").getValue().toString();
                }
                else if (AcRating > 2.5 && AcRating <= 3.5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("3").getValue().toString();
                }
                else if (AcRating > 3.5 && AcRating <= 4.5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("4").getValue().toString();
                }
                else if (AcRating > 4.5 && AcRating <= 5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("5").getValue().toString();
                }
                else if (AcRating > 3.75 && AcRating <= 3.25){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("3").getValue().toString();
                }
                else if (AcRating > 3.25 && AcRating <= 3.75){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child(d).getValue().toString();
                }
                else if (AcRating > 3.75 && AcRating <= 4.25){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("4").getValue().toString();
                }
                else if (AcRating > 4.25 && AcRating <= 4.75){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child(e).getValue().toString();
                }
                else if (AcRating > 4.75 && AcRating <= 5){
                    PresentRating = dataSnapshot.child(UserName).child("Rating").child("5").getValue().toString();
                }

                if (PresentRating.isEmpty()){
                    PresentRating = "0.0";
                }
                */
                Double Cummulative = Double.parseDouble(PresentRating) + 1;
                //AverageRating = Cummulative/2 ;
                String Comment = LoginName + ":";
                Double dou = new Double(Double.parseDouble(ActualRating));
                //AverageRating = dou.intValue();
                //AvgRating = " " + AverageRating;

                FeedbackRef.child(UserName).child("Rating").child("Rating").setValue(AverageRating);
                CommentsRef.child(Comment).setValue(Feedback);

                Intent intent = new Intent(Feedback.this, Home.class);
                intent.putExtra("Name", FullName);
                intent.putExtra("Category", CategorySelected);
                intent.putExtra("SubCategory", SubCategorySelected);
                intent.putExtra("Location", Location);
                intent.putExtra("UserName", LoginName);
                //finish();
                //finish();
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
