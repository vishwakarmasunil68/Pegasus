package com.bjain.pegasus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 12-06-2017.
 */

public class SchoolBooksFragment extends Fragment{

    @BindView(R.id.iv_school_image)
    ImageView iv_school_image;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_program)
    TextView tv_program;

    int index=0;

    public SchoolBooksFragment(int index){
        this.index=index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_school_programs,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String image_url="";
        String text="Books";
        String program="Each year, Pegasus publishes 200 new titles and editions that are available in more than 20 languages. With new, creatively written books that have been compiled by our team of expert, Pegasus has ensured that there is a variety of books available for every grade and level.";

        switch (index){
            case 0:
                image_url="http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/books.jpg";
                text="Books";
                program="Each year, Pegasus publishes 200 new titles and editions that are available in more than 20 languages. With new, creatively written books that have been compiled by our team of expert, Pegasus has ensured that there is a variety of books available for every grade and level.";
                break;
            case 1:
                image_url="http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/school.jpg";
                text="Schools";
                program="Pegasus books have been appreciated by education experts and teachers from across the world. More than 1000 schools in more than 50 countries have readily accepted and have made our books part of their learning programs in schools. Our books have also been part of many after school programs and advanced learning line ups.";
                break;
            case 2:
                image_url="http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/ministry.jpg";
                text="Ministry of Education";
                program="Pegasus has always ensured that its books maintain and value-add to the educational standards set by various countries. We have worked closely with Education Ministry of 10 countries on various school curriculum and library projects. This access and scope of our books has allowed our books to have a more global and universal viewpoint.";
                break;
            case 3:
                image_url="http://www.pegasusforkids.com/media/wysiwyg/School_Connect_Program/curriculum.jpg";
                text="Curriculum";
                program="With its well equipped team of experts, Pegasus has been developing, revising and improving curriculum for the finest school franchise in India (Shemrock and Mother's Pride).";
                break;
        }


        Glide.with(getActivity().getApplicationContext())
                .load(image_url)
                .placeholder(R.drawable.ic_school_connect)
                .dontAnimate()
                .into(iv_school_image);

        tv_title.setText(text);
        tv_program.setText(program);


    }
}
