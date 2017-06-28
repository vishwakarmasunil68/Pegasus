package com.bjain.pegasus.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjain.pegasus.R;
import com.bjain.pegasus.adapter.CustomswipeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 27-06-2017.
 */

public class PegasusBetterTommorowFragment extends Fragment {
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.tv_vision)
    TextView tv_vision;
    @BindView(R.id.tv_here)
    TextView tv_here;
    @BindView(R.id.tv_literacy)
    TextView tv_literacy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_better_tommorow, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> imageList = new ArrayList<>();
        imageList.add("http://www.pegasusforkids.com/media/em_minislideshow/1498219112_0_ban3.png");
        imageList.add("http://www.pegasusforkids.com/media/em_minislideshow/1498219112_1_ban2.png");
        imageList.add("http://www.pegasusforkids.com/media/em_minislideshow/1498219112_2_ban1.png");
        setUpViewPager(imageList);

        String text = "<h1>Our Vision</h1>";
        tv_vision.setText(Html.fromHtml(text));

        String text_here="<ul style=\"padding-left: 30px;\"><li style=\"color: #636363; list-style: none; font-size: 19px; font-family: Roboto; line-height: 27px; float: left; padding: 6px 0px;\">Here's is what we are trying to do with <strong>Pegasus One for One</strong>.</li><li style=\"color: #636363; font-size: 19px; font-family: Roboto; line-height: 27px; float: left; padding: 6px 0px; font-weight: normal;\"><strong style=\"color: #000000;\">How it works?</strong> It's simple! If you donate 10 books (doesn't matter what genre, shape or condition they are in), we will donate 10 new Pegasus books (storybooks, knowledge-based interactive books etc) to be distributed among underprivileged children.</li><li style=\"color: #636363; font-size: 19px; font-family: Roboto; line-height: 27px; float: left; padding: 6px 0px; font-weight: normal;\"><strong style=\"color: #000000;\">Who get the books?</strong> We will donate the books to underprivileged children of Delhi NCR who don't have any access to formal education and good quality books.</li><li style=\"color: #636363; font-size: 19px; font-family: Roboto; line-height: 27px; float: left; padding: 6px 0px; font-weight: normal;\"><strong style=\"color: #000000;\">We are not alone.</strong> Pegasus has joined hands with NGOs, including CRY and <strong>Robin Hood Army</strong>, to support education for the underprivileged.</li></ul>";
        tv_here.setText(Html.fromHtml(text_here));

        String text_literacy="<p style=\"color: #4c4c4c; font-size: 22px; line-height: 35x; font-family: Roboto; margin-top: 25px;\">Let's fight illiteracy together! We believe that books are every child's right and with our Pegasus One-for-One programme we hope to bring the joy of reading and learning to kids who have little access to good books. We have one aim-to promote literacy by giving underprivileged children access to new and quality books.</p>";
        tv_literacy.setText(Html.fromHtml(text_literacy));
    }

    public void setUpViewPager(final List<String> imageIdList) {
        view_pager.setAdapter(new CustomswipeAdapter(getActivity(), imageIdList, false));
        view_pager.setOffscreenPageLimit(imageIdList.size());
        if (imageIdList.size() > 0) {
            new CountDownTimer(999999999, 5000) {

                @Override
                public void onTick(long l) {
                    if ((view_pager.getCurrentItem() + 1) == imageIdList.size()) {
                        view_pager.setCurrentItem(0);
                    } else {
                        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                    }
                }

                @Override
                public void onFinish() {
                    start();
                }
            }.start();
        }
    }

}
