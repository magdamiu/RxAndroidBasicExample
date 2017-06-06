package com.magdamiu.rxandroidbasicexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by magdamiu on 31/05/17.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private List<String> mCountries;

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.title.setText(mCountries.get(position));
    }

    @Override
    public int getItemCount() {
        return mCountries == null ? 0 : mCountries.size();
    }

    public void setCountries(List<String> countries) {
        mCountries = countries;
        notifyDataSetChanged();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public CountryViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}

