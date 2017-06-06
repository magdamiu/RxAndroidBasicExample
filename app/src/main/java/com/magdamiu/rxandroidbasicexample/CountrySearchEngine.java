package com.magdamiu.rxandroidbasicexample;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by magdamiu on 31/05/17.
 */

public class CountrySearchEngine {
    private final List<String> mCountries;
    private final int mCountriesCount;

    public CountrySearchEngine(List<String> countries) {
        mCountries = countries;
        mCountriesCount = mCountries.size();
    }

    public List<String> search(String query) {
        query = query.toLowerCase();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> result = new LinkedList<>();

        for (int i = 0; i < mCountriesCount; i++) {
            if (mCountries.get(i).toLowerCase().contains(query)) {
                result.add(mCountries.get(i));
            }
        }
        return result;
    }
}
