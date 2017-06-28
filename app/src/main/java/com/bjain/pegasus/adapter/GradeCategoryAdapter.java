package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjain.pegasus.R;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class GradeCategoryAdapter extends RecyclerView.Adapter<GradeCategoryAdapter.MyViewHolder> {

    private List<String> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_grade_category;

        public MyViewHolder(View view) {
            super(view);
            tv_grade_category = (TextView) view.findViewById(R.id.tv_grade_category);

        }
    }


    public GradeCategoryAdapter(Activity activity, List<String> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_grade_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_grade_category.setText(horizontalList.get(position));

    }


    @Override
    public int getItemCount() {
        if (horizontalList != null) {
            return horizontalList.size();
        } else {
            return 0;
        }
    }

}
