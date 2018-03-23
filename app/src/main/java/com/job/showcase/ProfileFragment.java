package com.job.showcase;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.job.showcase.userManagement.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View mRootView;
    private FirebaseAuth mAuth;
    public ProfileFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,mRootView);

        mAuth = FirebaseAuth.getInstance();
        return mRootView;
    }

    @OnClick(R.id.profilebtn_logout)
    public void userLogout(){
        mAuth.signOut();
        Intent intent = new Intent(mRootView.getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
