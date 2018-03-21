package com.job.showcase;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.job.showcase.adapter.PagerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.profileLabel)
    TextView tv_profile;
    @BindView(R.id.usersLabel)
    TextView tv_allUsers;
    @BindView(R.id.notificationLabel)
    TextView tv_notification;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    PagerViewAdapter pagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerViewAdapter);
    }
}
