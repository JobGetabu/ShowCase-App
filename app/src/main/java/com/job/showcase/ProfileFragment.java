package com.job.showcase;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.showcase.userManagement.LoginActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.frg_profile_image)
    CircleImageView profileFrgPic;
    @BindView(R.id.frg_profile_name)
    TextView profileFrgName;
    @BindView(R.id.frg_profile_progressBar)
    ProgressBar progressBar;

    private View mRootView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    public static final String USERS_COLLECTION = "Users";
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
        mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        loadFragmentData(currentUser);

        return mRootView;
    }

    private void loadFragmentData(FirebaseUser cUser){
        mFirestore.collection(USERS_COLLECTION).document(cUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = documentSnapshot.getString("name");
                        String image_url = documentSnapshot.getString("imageurl");

                        RequestOptions placeholderRequestOptions = new RequestOptions();
                        placeholderRequestOptions.placeholder(R.drawable.profile_placeholder);

                        profileFrgName.setText(name);
                        Glide.with(mRootView.getContext())
                                .setDefaultRequestOptions(placeholderRequestOptions)
                                .load(image_url)
                                .into(profileFrgPic);


                    }
                });

    }

    @OnClick(R.id.profilebtn_logout)
    public void userLogout(){
        progressBar.setVisibility(View.VISIBLE);
        String user_id = mAuth.getCurrentUser().getUid();
        HashMap tokenHash = new HashMap();
        tokenHash.put("token_id","");

        mFirestore.collection("Users").document(user_id).update(tokenHash)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mAuth.signOut();
                        Intent intent = new Intent(mRootView.getContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                });

    }
}
