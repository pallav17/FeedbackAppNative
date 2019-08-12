package com.pallav.feedbacknative.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pallav.feedbacknative.InsertFeedbackActivity;
import com.pallav.feedbacknative.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EmpListAdapter extends RecyclerView.Adapter<EmpListAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arrData;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public RelativeLayout itemView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.full_name);
            itemView = v.findViewById(R.id.itemView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EmpListAdapter(Context context, ArrayList<HashMap<String, String>> arrData) {
        this.context = context;
        this.arrData = arrData;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public EmpListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_name_view, null);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(arrData.get(position).get("name"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InsertFeedbackActivity.class);
                i.putExtra("name", arrData.get(position).get("name"));
                context.startActivity(i);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrData.size();
    }
}