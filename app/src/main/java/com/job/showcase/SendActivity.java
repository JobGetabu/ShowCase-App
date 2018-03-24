package com.job.showcase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendActivity extends AppCompatActivity {

    @BindView(R.id.send_notification_sendto_name)
    TextView sendTo_tv;
    @BindView(R.id.send_notification_text)
    TextView notfMesssage;
    @BindView(R.id.send_notification_progressBar)
    ProgressBar mProgressbar;
    @BindView(R.id.send_notification_button)
    Button sendNotfButton;

    private String currentUserId;
    private String mUserId;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        ButterKnife.bind(this);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mFireStore = FirebaseFirestore.getInstance();

        mUserId = getIntent().getStringExtra("user_id");
        String user_name = getIntent().getStringExtra("user_name");
        sendTo_tv.setText("Send to "+user_name);
    }

    @OnClick(R.id.send_notification_button)
    public void sendNotificationClick(){

        if (!TextUtils.isEmpty(notfMesssage.getText())){
            mProgressbar.setVisibility(View.VISIBLE);
            String message = notfMesssage.getText().toString();
            HashMap notification = new HashMap();

            notification.put("notification",message);
            notification.put("from",currentUserId);

            mFireStore.collection("Users/"+ mUserId +"/Notifications").add(notification)
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            notfMesssage.setText("");
                            Toast.makeText(SendActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            mProgressbar.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SendActivity.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
