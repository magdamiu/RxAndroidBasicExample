package com.magdamiu.rxandroidbasicexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Button searchButton;
    protected EditText countryEditText;
    private ProgressBar mProgressBar;

    protected CountrySearchEngine mCountrySearchEngine;
    private CountryAdapter mCountryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.btn_search);
        countryEditText = (EditText) findViewById(R.id.et_search);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mCountryAdapter = new CountryAdapter());

        List<String> countries = Arrays.asList(getResources().getStringArray(R.array.countries_array));
        mCountrySearchEngine = new CountrySearchEngine(countries);
    }

    protected void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    protected void showResult(List<String> result) {
        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show();
            mCountryAdapter.setCountries(Collections.<String>emptyList());
        } else {
            mCountryAdapter.setCountries(result);
        }
    }
}
