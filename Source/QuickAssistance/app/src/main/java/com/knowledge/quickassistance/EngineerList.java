package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EngineerList extends AppCompatActivity {
    String categorySelected, subCategorySelected, location, Name, userName;
    TextView Category,SubCategory,ULocation;
    ArrayList<HashMap<String,String>> TechList = new ArrayList<>();
    String[] engineerName = new String[10];
    String[] experience = new String[10];
    String[] ratings = new String[10];
    String[] basecharges = new String[10];
    String[] TechUserNames = new String[10];
    ListView list;
    int i=0;
    String TechName, TechFirstName, TechLastName, TechFullName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ListRef;
    TechListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineers_list);
        Intent intent = getIntent();
        categorySelected = intent.getStringExtra("category");
        subCategorySelected = intent.getStringExtra("SubCategory");
        userName = intent.getStringExtra("UserName");
        location = intent.getStringExtra("location");
        Category = (TextView)findViewById(R.id.CategorytextView);
        SubCategory = (TextView)findViewById(R.id.SubCategorytextView);
        Category.setText(categorySelected);
        SubCategory.setText(subCategorySelected);


        Resources res=getResources();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ListRef = firebaseDatabase.getReference("Engineers");
        list=(ListView) findViewById(R.id.EngineerListView);
        adapter=new TechListAdapter(this, engineerName, experience, basecharges, ratings);
        list.setAdapter(adapter);
        Query CategoryQuery = ListRef.orderByChild("Zipcode").equalTo(location);
        CategoryQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, String> TechMap = new HashMap<String, String>();
                String FullName, Experience, Fare, TechRating;

                for (DataSnapshot category : dataSnapshot.getChildren()) {
                    if (category.child("Expertise").getValue().equals(categorySelected)) {
                        if (category.child("SubCategory").hasChild(subCategorySelected)) {
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            FullName = category.child("FirstName").getValue().toString() + " " + category.child("LastName").getValue().toString();
                            Experience = category.child("experience").getValue().toString();
                            Fare = category.child("SubCategory").child(subCategorySelected).getValue().toString();
                            TechRating = category.child("Rating").child("Rating").getValue().toString();
                            TechName = category.child("UserName").getValue().toString();
                            Name = FullName;
                            TechMap.put("FullName", FullName);
                            engineerName[i] = FullName;
                            TechMap.put("experience", Experience);
                            experience[i] = Experience;
                            TechMap.put("BaseFare", Fare);
                            basecharges[i] = Fare;
                            TechMap.put("Rating", TechRating);
                            ratings[i] = TechRating;
                            TechMap.put("UserName", TechName);
                            TechUserNames[i] = TechName;
                            TechList.add(TechMap);
                            i++;
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EngineerList.this, EngineerDetail.class);
                final String engineer = (String) parent.getItemAtPosition(position);
                int Position = (int) parent.getItemIdAtPosition(position);
                /*ListRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot TechNamesList : dataSnapshot.getChildren()){
                            TechFirstName = TechNamesList.child("FirstName").getValue().toString();
                            TechLastName = TechNamesList.child("LastName").getValue().toString();
                            TechFullName = TechFirstName + " " + TechLastName;
                            if (TechFullName.contentEquals(engineer)){
                                TechName = TechNamesList.child("UserName").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });*/
                intent.putExtra("name", engineer);
                intent.putExtra("engineerUserName", TechUserNames[Position]);
                intent.putExtra("UserName",userName);
                intent.putExtra("category", categorySelected);
                intent.putExtra("SubCategory", subCategorySelected);
                intent.putExtra("location", location);
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
    public class TechListAdapter extends ArrayAdapter<String> {

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
