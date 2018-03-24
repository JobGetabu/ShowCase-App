package com.job.showcase.pushNotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.job.showcase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationReceiverActivity extends AppCompatActivity {

    @BindView(R.id.receive_notification_from_name)
    TextView fromName;
    @BindView(R.id.receiver_notification_message)
    TextView messageReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);

        ButterKnife.bind(this);

        String name = getIntent().getStringExtra("message");
        String message = getIntent().getStringExtra("from_id");

        fromName.setText(name);
        messageReceived.setText(message);
    }
}
