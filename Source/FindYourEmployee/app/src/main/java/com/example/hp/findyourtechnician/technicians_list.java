package com.example.hp.findyourtechnician;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class technicians_list extends AppCompatActivity {

    String CategorySelected,SubCategorySelected,Location, Name, Uname;
    TextView Category,SubCategory,ULocation;
    ArrayList<HashMap<String,String>> TechList = new ArrayList<>();
    String[] techniciannames = new String[10];
    String[] experience = new String[10];
    String[] ratings = new String[10];
    String[] basecharges = new String[10];
    String[] TechUserNames = new String[10];
    ListView list;
    int i=0;
    String TechName, TechFirstName, TechLastName, TechFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technicians_list);
        Intent intent = getIntent();
        CategorySelected = intent.getStringExtra("Category");
        SubCategorySelected = intent.getStringExtra("SubCategory");
        Uname = intent.getStringExtra("UserName");
        Location = intent.getStringExtra("Location");
        Category = (TextView)findViewById(R.id.CategorytextView);
        SubCategory = (TextView)findViewById(R.id.SubCategorytextView);
        Category.setText(CategorySelected);
        SubCategory.setText(SubCategorySelected);


        Resources res=getResources();
        //techniciannames=res.getStringArray(R.array.Technician_names);
        //experience=res.getStringArray(R.array.Experience);
        //basecharges=res.getStringArray(R.array.Base_Charges);
        //ratings=res.getStringArray(R.array.Ratings);


        Firebase.setAndroidContext(this);
        final Firebase ListRef = new Firebase("https://findyourtechnician.firebaseio.com/Technicians");
        //ListRef.child(Uname).child("Rating").child("Count").setValue(0);
        //ListRef.child(Uname).child("Rating").child("Rating").setValue(0);
        Query CategoryQuery = ListRef.orderByChild("Zipcode").equalTo(Location);
        CategoryQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, String> TechMap = new HashMap<String, String>();
                String FullName, Experience, Fare, TechRating;

                for (DataSnapshot category : dataSnapshot.getChildren()) {
                    if (category.child("Expertise").getValue().equals(CategorySelected)) {
                        if (category.child("SubCategory").hasChild(SubCategorySelected)) {
                            FullName = category.child("FirstName").getValue().toString() + " " + category.child("LastName").getValue().toString();
                            Experience = category.child("Experience").getValue().toString();
                            Fare = category.child("SubCategory").child(SubCategorySelected).getValue().toString();
                            TechRating = category.child("Rating").child("Rating").getValue().toString();
                            TechName = category.child("UserName").getValue().toString();
                            Name = FullName;
                            TechMap.put("FullName", FullName);
                            techniciannames[i] = FullName;
                            TechMap.put("Experience", Experience);
                            experience[i] = Experience;
                            TechMap.put("BaseFare", Fare);
                            basecharges[i] = Fare;
                            TechMap.put("Rating", TechRating);
                            ratings[i] = TechRating;
                            TechMap.put("UserName", TechName);
                            TechUserNames[i] = TechName;
                            TechList.add(TechMap);
                            i++;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        list=(ListView) findViewById(R.id.TechnicianlistView);
        TechListAdapter adapter=new TechListAdapter(this, techniciannames, experience, basecharges, ratings);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(technicians_list.this, TechnicianDetails.class);
                final String Technician = (String) parent.getItemAtPosition(position);
                int Position = (int) parent.getItemIdAtPosition(position);
                /*ListRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot TechNamesList : dataSnapshot.getChildren()){
                            TechFirstName = TechNamesList.child("FirstName").getValue().toString();
                            TechLastName = TechNamesList.child("LastName").getValue().toString();
                            TechFullName = TechFirstName + " " + TechLastName;
                            if (TechFullName.contentEquals(Technician)){
                                TechName = TechNamesList.child("UserName").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });*/
                intent.putExtra("Name", Technician);
                intent.putExtra("TUserName", TechUserNames[Position]);
                intent.putExtra("UserName",Uname);
                intent.putExtra("Category", CategorySelected);
                intent.putExtra("SubCategory", SubCategorySelected);
                intent.putExtra("Location", Location);
                startActivity(intent);
            }
        });
    }

    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
/*
    public void showAlert(View view){
        AlertDialog.Builder alertD = new AlertDialog.Builder(this);
        String a = "" + TechList.get(0);
        alertD.setMessage(a).create();
        alertD.show();
    }
*/
    public class TechListAdapter extends ArrayAdapter<String>{

    Context context;
    String[] TechTitles;
    String[] TechCharges;
    String[] TechExperience;
    String[] Techratings;
    TechListAdapter(Context c, String[] titles, String[] experience, String[] charges, String[] ratings)
    {
        super(c, R.layout.row_layout, R.id.TechnicianNametextView, titles);
        this.context = c;
        this.TechTitles = titles;
        this.TechExperience = experience;
        this.TechCharges = charges;
        this.Techratings = ratings;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.row_layout,parent,false);

            TextView tech = (TextView) v.findViewById(R.id.TechnicianNametextView);
            TextView Exp = (TextView) v.findViewById(R.id.ExperiencetextView);
            TextView Charge = (TextView) v.findViewById(R.id.BaseChargestextView);
            TextView Rating = (TextView) v.findViewById(R.id.RatingstextView);

            tech.setText(TechTitles[position]);
            Exp.setText(TechExperience[position]);
            Charge.setText(TechCharges[position]);
            Rating.setText(Techratings[position]);

            return v;
        }
    }

}

