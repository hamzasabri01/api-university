package com.example.apiuniversity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private RecyclerView recyclerView;
    private List<University> universities = new ArrayList<>();
    private UniversitiesAdapter universitiesAdapter;
    private UniversitiesService universitiesService;
    private UniversitiesVolleyService universitiesVolleyService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        spinner = findViewById(R.id.spinner_country);
        recyclerView = findViewById(R.id.recycler_view);

        universitiesAdapter = new UniversitiesAdapter(universities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(universitiesAdapter);

        final boolean[] first = {true};

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, R.layout.spnr_qualification);
        adapter.setDropDownViewResource(R.layout.drpdn_qual);
        spinner.setAdapter(adapter);

        universitiesService = new UniversitiesService();
        universitiesVolleyService = new UniversitiesVolleyService(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country = parent.getItemAtPosition(position).toString();

                if(first[0]){
                    first[0] = false;
                }else {

                    if (position == 0) {
                        Toast.makeText(MainActivity.this, "Please select appropriate option!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, country + " Selected !", Toast.LENGTH_SHORT).show();
                    }
                }

                universitiesService.getUniversities(country, new UniversitiesService.Callback<List<University>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(List<University> result) {
                        universities.clear();
                        universities.addAll(result);
                        universitiesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

                universitiesVolleyService.getUniversities(country, new UniversitiesVolleyService.VolleyCallback<List<University>>() {
                    @Override
                    public void onSuccess(List<University> result) {
                        universities.clear();
                        universities.addAll(result);
                        universitiesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire
            }
        });

    }
}
