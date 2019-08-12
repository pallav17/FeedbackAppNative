package com.pallav.feedbacknative.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pallav.feedbacknative.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arrData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.full_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FeedbackListAdapter(ArrayList<HashMap<String, String>> arrData) {
        this.arrData = arrData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FeedbackListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, null);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(arrData.get(position).get("name"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrData.size();
    }
}