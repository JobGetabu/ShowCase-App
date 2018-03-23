package com.job.showcase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.job.showcase.adapter.UserRecylerViewAdapter;
import com.job.showcase.model.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    @BindView(R.id.frg_users_recyclerview)
    RecyclerView recyclerView;

    private List<Users> usersList;
    UserRecylerViewAdapter userRecylerViewAdapter;
    private FirebaseFirestore mFirestore;


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this,view);

        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        userRecylerViewAdapter = new UserRecylerViewAdapter(usersList,container.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(userRecylerViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mFirestore.collection("Users")
                .addSnapshotListener( getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){

                           if (documentChange.getType() == DocumentChange.Type.ADDED){

                               Users users = documentChange.getDocument().toObject(Users.class);
                               usersList.add(users);

                               userRecylerViewAdapter.notifyDataSetChanged();
                           }
                        }
                    }
                });
    }
}
