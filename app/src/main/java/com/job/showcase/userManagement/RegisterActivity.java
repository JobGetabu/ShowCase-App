package com.job.showcase.userManagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.job.showcase.MainActivity;
import com.job.showcase.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.reg_email)
    TextView regemail;
    @BindView(R.id.reg_name)
    TextView regName;
    @BindView(R.id.reg_password)
    TextView regPassword;
    @BindView(R.id.button_new_account)
    Button newAccountBtn;
    @BindView(R.id.button_back_to_login)
    Button backTologin;
    @BindView(R.id.profile_imageBtn)
    CircleImageView profileImgbtn;
    @BindView(R.id.register_progressBar)
    ProgressBar progressBar;

    public static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //layout init
        ButterKnife.bind(this);
        imageUri = null;
        storageReference = FirebaseStorage.getInstance().getReference().child("profileimages");
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

    }

    @OnClick(R.id.button_back_to_login)
    public void sendBackTologin() {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.profile_imageBtn)
    public void picImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            profileImgbtn.setImageURI(imageUri);
        }
    }

    @OnClick(R.id.button_new_account)
    public void registerNewUser() {
        //few strategies on handling signup
        //create a account management class to handle registration
        // and initial creation of an account

     /*   if (imageUri == null) {
            startSignUP();
        } else {
            startUploading() {
                startSignUp();
            }
        }*/

        if (imageUri != null) {
            final String name = regName.getText().toString();
            String email = regemail.getText().toString();
            String password = regPassword.getText().toString();
            progressBar.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> authResult) {
                                if (authResult.isSuccessful()) {
                                    //A good place to send user to acoountSetUpActivity
                                    final String user_id = mAuth.getCurrentUser().getUid();

                                    StorageReference userProfile = storageReference.child(user_id + ".jpg");
                                    userProfile.putFile(imageUri)
                                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadtask) {
                                                    if (uploadtask.isSuccessful()) {

                                                        String download_url = uploadtask.getResult().getDownloadUrl().toString();

                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                        hashMap.put("name", name);
                                                        hashMap.put("imageurl", download_url);

                                                        mFirestore.collection("Users").document(user_id).set(hashMap)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        sendToMain();
                                                                    }
                                                                });

                                                    } else {
                                                        Toast.makeText(RegisterActivity.this,
                                                                "Image upload Error " + uploadtask.getException(), Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            "Error creating account " + authResult.getException(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
