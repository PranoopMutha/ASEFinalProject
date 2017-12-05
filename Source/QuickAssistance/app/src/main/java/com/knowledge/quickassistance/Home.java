package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Home extends AppCompatActivity {

    String[] tech;
    String[] techDesc;
    int[] techimages = {R.drawable.icon, R.drawable.icon, R.drawable.icon, R.drawable.icon, R.drawable.icon};
    ListView list;
    EditText zipCode;
    Button HomeActivity_EditProfile;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        zipCode = (EditText) findViewById(R.id.LocationSearch);
        HomeActivity_EditProfile = (Button)findViewById(R.id.HomeEditProfileButton);

        Intent LoginIntent = getIntent();
        Name = LoginIntent.getStringExtra("UserName");
        Resources res = getResources();
        tech = res.getStringArray(R.array.engineers);
        techDesc = res.getStringArray(R.array.engineerDescriptions);
        list = (ListView) findViewById(R.id.EngineersList);
        SampleAdapter SA = new SampleAdapter(this,tech,techimages, techDesc);
        list.setAdapter(SA);
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        Intent intent = new Intent(Home.this, SubCategory.class);
                        String CategorySelected = (String) parent.getItemAtPosition(position);
                        intent.putExtra("category", CategorySelected);
                        String UserAddress = zipCode.getText().toString();
                        intent.putExtra("location", UserAddress);
                        intent.putExtra("UserName", Name);
                        startActivity(intent);
                    }
                });
    }
    public void EditProfile(View view)
    {
        Intent intent = new Intent(Home.this,EditProfile.class);
        intent.putExtra("UserName",Name);
        startActivity(intent);
    }
    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
class SampleAdapter extends ArrayAdapter<String> {
    Context context;
    int[] techImages;
    String[] TechTitles;
    String[] TechDesc;

    SampleAdapter(Context c, String[] titles, int images[], String[] description) {
        super(c, R.layout.single_row, R.id.New_User, titles);
        this.context = c;
        this.techImages = images;
        this.TechTitles = titles;
        this.TechDesc = description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.single_row, parent, false);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView tech = (TextView) v.findViewById(R.id.New_User);
        img.setImageResource(techImages[position]);
        tech.setText(TechTitles[position]);
        return v;
    }
}
