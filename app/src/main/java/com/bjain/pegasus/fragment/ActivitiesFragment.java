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

public class ActivitiesFragment extends Fragment{
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_teacher_training_program)
    ImageView iv_teacher_training_program;
    @BindView(R.id.iv_story_telling_session)
    ImageView iv_story_telling_session;
    @BindView(R.id.iv_school_book_fair)
    ImageView iv_school_book_fair;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_activities,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/book-fair1.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_top);
        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/book-fair2.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_school_book_fair);
        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/TeacherTrainingProgram.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_teacher_training_program);
        Glide.with(getActivity().getApplicationContext())
                .load("http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/storytelling-sessions.jpg")
                .error(R.drawable.ic_school_connect)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_story_telling_session);


    }
}
