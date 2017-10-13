package com.example.hp.findyourtechnician;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Home extends AppCompatActivity {

    String[] tech;
    String[] techDesc;
    int[] techimages = {R.drawable.carpenter, R.drawable.electrician, R.drawable.painter, R.drawable.plumber, R.drawable.mechanic};
    ListView list;
    private GoogleMap mMap;
    LocationManager PresentLocation;
    LocationListener PresentLocationListener;
    LatLng PresentLocationLatLng;
    Geocoder PresentLocationGeocoder;
    double PresentLocationLatitude = 0;
    double PresentLocationLongitude = 0;
    StringBuilder UserPresentAddress;
    EditText s;
    Button HomeActivity_EditProfile;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        s = (EditText) findViewById(R.id.Location_Search);
        HomeActivity_EditProfile = (Button)findViewById(R.id.Home_EditProfileButton);

        Intent LoginIntent = getIntent();
        Name = LoginIntent.getStringExtra("UserName");

/*
        PresentLocationGeocoder = new Geocoder(this);
        PresentLocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        PresentLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        PresentLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, PresentLocationListener);
        //PresentLocationLatitude = PresentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        //PresentLocationLongitude = PresentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

        if(PresentLocation != null) {
            PresentLocationLatLng = new LatLng(PresentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), PresentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
        }

        //PresentLocationLatLng = new LatLng();

        try {

            List<Address> PresentLocationAddresses = PresentLocationGeocoder.getFromLocation(PresentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), PresentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude(), 1);
            Address PresentLocationAddress = PresentLocationAddresses.get(0);
            UserPresentAddress = new StringBuilder();


            for (int i = PresentLocationAddress.getMaxAddressLineIndex()-1; i < PresentLocationAddress.getMaxAddressLineIndex(); i++) {

                UserPresentAddress.append(PresentLocationAddress.getAddressLine(i)).append("\t");

            }

            int ind = PresentLocationAddress.getMaxAddressLineIndex()-1;
            int startind = (PresentLocationAddress.getAddressLine(ind).length())-5;
            int lastind = PresentLocationAddress.getAddressLine(ind).length();
            UserPresentAddress.append("sm");

            StringBuilder PresentAdd = new StringBuilder();
            //UserPresentAddress = null;

            PresentAdd.append(PresentLocationAddress.getAddressLine(ind).substring(startind, lastind));

            //ind = UserPresentAddress.length();

            //UserPresentAddress.append(PresentLocationAddress.getCountryName()).append("\t");


            String addr = PresentAdd.toString();
            EditText addres = (EditText)findViewById(R.id.Location_Search);
            //EditText addres = (EditText) findViewById(R.id.Location_Search);
            addres.setText(addr);
            //addres.setQuery(addr,true);

            //EditText address2 = (EditText) findViewById(R.id.Address2);
            //address2.setText(PresentLocationAddress.getCountryName());
            //TextView address = (TextView) findViewById(R.id.Address);
            //address.setText(addr);

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        Resources res = getResources();
        tech = res.getStringArray(R.array.technicians);
        techDesc = res.getStringArray(R.array.technicianDescriptions);
        list = (ListView) findViewById(R.id.Technicians_List);
        sampleAdapter SA = new sampleAdapter(this,tech,techimages, techDesc);
        list.setAdapter(SA);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        Intent intent = new Intent(Home.this, SubCategoryPage.class);
                        String CategorySelected = (String) parent.getItemAtPosition(position);
                        intent.putExtra("Category", CategorySelected);
                        String UserAddress = s.getText().toString();
                        intent.putExtra("Location", UserAddress);
                        intent.putExtra("UserName", Name);
                        startActivity(intent);

                    }
                });

    }

    public void EditProfile(View view)
    {
        Intent intent = new Intent(Home.this,EditProfilePage.class);
        intent.putExtra("UserName",Name);
        startActivity(intent);
    }

    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}

class sampleAdapter extends ArrayAdapter<String>
{
    Context context;
    int[] techImages;
    String[] TechTitles;
    String[] TechDesc;
    sampleAdapter(Context c, String[] titles, int images[], String[] description)
    {
        super(c, R.layout.single_row, R.id.New_User, titles);
        this.context = c;
        this.techImages = images;
        this.TechTitles = titles;
        this.TechDesc = description;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.single_row,parent,false);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView tech = (TextView) v.findViewById(R.id.New_User);
        //TextView techDesc = (TextView) v.findViewById(R.id.textView2);
        img.setImageResource(techImages[position]);
        tech.setText(TechTitles[position]);
        //techDesc.setText(TechDesc[position]);

        return v;
    }
}
