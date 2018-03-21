package com.job.showcase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.job.showcase.NotificationFragment;
import com.job.showcase.ProfileFragment;
import com.job.showcase.UsersFragment;

/**
 * Created by Job on Thursday : 3/22/2018.
 */

public class PagerViewAdapter extends FragmentPagerAdapter {

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

       switch (position){
           case 0:
               return new ProfileFragment();
           case 1:
               return new UsersFragment();
           case 2:
               return new NotificationFragment();
       }
       return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
