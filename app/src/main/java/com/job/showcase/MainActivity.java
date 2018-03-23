package com.job.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.job.showcase.adapter.PagerViewAdapter;
import com.job.showcase.userManagement.LoginActivity;

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
    FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            sendToLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerViewAdapter);

        mViewPager.addOnPageChangeListener(pageChangeListener);
        //caching of offscreenpages
        mViewPager.setOffscreenPageLimit(2);

        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });
        tv_allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });
        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2);
            }
        });
    }

    private void sendToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
                changeTabs(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //@TargetApi(Build.VERSION_CODES.M)
    private void changeTabs(int position) {

        if (position == 0){
            tv_profile.setTextColor(ContextCompat.getColor(this,R.color.textTabBright));
            tv_profile.setTextSize(22);

            tv_allUsers.setTextSize(16);
            tv_allUsers.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

            tv_notification.setTextSize(16);
            tv_notification.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
        if (position == 1){
            tv_profile.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            tv_profile.setTextSize(16);

            tv_allUsers.setTextSize(22);
            tv_allUsers.setTextColor(ContextCompat.getColor(this,R.color.textTabBright));

            tv_notification.setTextSize(16);
            tv_notification.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
        if (position == 2){
            tv_profile.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            tv_profile.setTextSize(16);

            tv_allUsers.setTextSize(16);
            tv_allUsers.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

            tv_notification.setTextSize(22);
            tv_notification.setTextColor(ContextCompat.getColor(this,R.color.textTabBright));
        }

    }
}
