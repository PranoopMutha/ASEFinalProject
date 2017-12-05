package com.knowledge.quickassistance;

/**
 * Created by vinay on 11/25/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SubCategory extends AppCompatActivity {
    Spinner ActivitySubCategory_Spinner;
    ArrayAdapter<CharSequence> ActivitySubCategory_adapter;
    String SubCategorySelected,Category,Location, Uname;
    TextView ActivitySubCategory_DescriptionTextView,ErrorText;
    EditText ActivitySubCategory_Description;
    Button ActivitySubCategory_GetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_page);
        Intent intent = getIntent();
        Category = intent.getStringExtra("category");
        Location = intent.getStringExtra("location");
        Uname = intent.getStringExtra("UserName");

        ActivitySubCategory_Spinner = (Spinner)findViewById(R.id.ContentEngineerDetails_spinner);
        if(Category.equalsIgnoreCase("Front End Developer"))
            ActivitySubCategory_adapter = ArrayAdapter.createFromResource(this,R.array.FrontEnd_SubCategory,android.R.layout.simple_spinner_item);
        else if(Category.equalsIgnoreCase("Back End Developer"))
            ActivitySubCategory_adapter = ArrayAdapter.createFromResource(this,R.array.BackEnd_SubCategory,android.R.layout.simple_spinner_item);
        else if(Category.equalsIgnoreCase("Full Stack Developer"))
            ActivitySubCategory_adapter = ArrayAdapter.createFromResource(this,R.array.FullStack_SubCategory,android.R.layout.simple_spinner_item);
        else
            ActivitySubCategory_adapter = ArrayAdapter.createFromResource(this,R.array.Tester_SubCategory,android.R.layout.simple_spinner_item);

        ActivitySubCategory_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActivitySubCategory_Spinner.setAdapter(ActivitySubCategory_adapter);
        ActivitySubCategory_DescriptionTextView = (TextView)findViewById(R.id.ContentSubCategory_DescriptionTextView);
        ActivitySubCategory_Description = (EditText)findViewById(R.id.ContentSubCategory_Description);
        ActivitySubCategory_GetList = (Button)findViewById(R.id.ContentSubCategory_GetButton);
        ErrorText = (TextView)findViewById(R.id.ErrorTextView);
        ActivitySubCategory_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubCategorySelected = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void getEngineerList(View view)
    {
        if(SubCategorySelected.equalsIgnoreCase("Other")&&(ActivitySubCategory_Description.getText().toString().trim().equalsIgnoreCase(""))){
            ErrorText.setVisibility(View.VISIBLE);
        }
        else {
            Intent intent = new Intent(SubCategory.this, EngineerList.class);
            intent.putExtra("category", Category);
            intent.putExtra("UserName",Uname);
            intent.putExtra("location",Location);
            intent.putExtra("SubCategory", SubCategorySelected);
            startActivity(intent);
        }
    }

    public void SignOut(View view){
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
