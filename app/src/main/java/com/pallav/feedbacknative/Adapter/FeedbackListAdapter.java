package com.pallav.feedbacknative.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pallav.feedbacknative.R;

import java.util.ArrayList;
import java.util.HashMap;


public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arrData;
    private final int limit = 20;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtSubject, txt_like, txt_suggestion, feedback_sender, txt_date;
        public RatingBar ratingBar;

        public MyViewHolder(View v) {
            super(v);
            txtSubject = v.findViewById(R.id.txtSubject);
            txt_like = v.findViewById(R.id.txt_like);
            txt_suggestion = v.findViewById(R.id.txt_suggestion);
            feedback_sender = v.findViewById(R.id.feedback_sender);
            txt_date = v.findViewById(R.id.txt_date);

            ratingBar = v.findViewById(R.id.ratingBar);



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
        holder.txtSubject.setText(arrData.get(position).get("Subject"));
        // holder.textView.setText(arrData.get(position).get("FirstName"));
        holder.txt_like.setText(arrData.get(position).get("Description"));
        holder.txt_suggestion.setText(arrData.get(position).get("Suggestion"));
        holder.feedback_sender.setText(arrData.get(position).get("FirstName") + " " + arrData.get(position).get("LastName"));
        holder.txt_date.setText(arrData.get(position).get("FeedbackDate"));

        holder.ratingBar.setRating(Float.valueOf(arrData.get(position).get("Rating")));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if (arrData.size() > limit) {

            return limit;
        } else {

            return arrData.size();
        }
    }

}

