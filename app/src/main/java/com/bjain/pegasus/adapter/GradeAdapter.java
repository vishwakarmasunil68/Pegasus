package com.bjain.pegasus.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.fragment.GradeFragment;
import com.bjain.pegasus.utils.TagUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.MyViewHolder> {

    private List<String> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private final String TAG = getClass().getSimpleName();
    List<LinearLayout> gradeLinears=new ArrayList<>();
    List<TextView> gradeTexts=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_grade_name;
        public LinearLayout ll_grade;

        public MyViewHolder(View view) {
            super(view);
            tv_grade_name = (TextView) view.findViewById(R.id.tv_grade_name);
            ll_grade = (LinearLayout) view.findViewById(R.id.ll_grade);

        }
    }


    public GradeAdapter(Activity activity,Fragment fragment, List<String> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment=fragment;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_grade_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_grade_name.setText(horizontalList.get(position));
        holder.tv_grade_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradeFragment gradeFragment= (GradeFragment) fragment;
                gradeFragment.gradeRVClickedPosition(position);
            }
        });
        if(!gradeLinears.contains(holder.ll_grade)) {
            gradeLinears.add(holder.ll_grade);
            gradeTexts.add(holder.tv_grade_name);
        }

        holder.ll_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TagUtils.getTag(),"list size:-"+gradeLinears.size());
//                for(int i=0;i<horizontalList.size();i++){
//                    gradeLinears.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    gradeTexts.get(i).setTextColor(Color.parseColor("#000000"));
//                }
//
//                gradeTexts.get(position).setTextColor(Color.parseColor("#FFFFFF"));
//                gradeLinears.get(position).setBackgroundColor(Color.parseColor("#bf1e2d"));

            }
        });

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
