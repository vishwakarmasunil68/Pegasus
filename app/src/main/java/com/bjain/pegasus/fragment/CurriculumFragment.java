package com.bjain.pegasus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bjain.pegasus.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 24-06-2017.
 */

public class CurriculumFragment extends Fragment{
    @BindView(R.id.iv_curriculum_building)
    ImageView iv_curriculum_building;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_curriculum,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/Pegasus-banner2.png")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_curriculum_building);


    }
}
