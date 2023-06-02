package com.example.apiuniversity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UniversityDetailsActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_details);

        mNameTextView = findViewById(R.id.tv_name);
        mLocationTextView = findViewById(R.id.tv_country);

        Intent intent = getIntent();
        University university = (University) intent.getSerializableExtra("UNIVERSITY");

        mNameTextView.setText(university.getName());
        mLocationTextView.setText(university.getCountry());
    }
}
