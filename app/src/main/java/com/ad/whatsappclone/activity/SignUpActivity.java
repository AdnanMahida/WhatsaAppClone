package com.ad.whatsappclone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.whatsappclone.databinding.ActivitySignUpBinding;
import com.ad.whatsappclone.models.Constraints;
import com.ad.whatsappclone.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        binding.signupBtnSignup.setOnClickListener(view -> {

            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(binding.signupEdtEmail.getText().toString(),
                    binding.signupEdtPassword.getText().toString())
                    .addOnCompleteListener(task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Users user = new Users(binding.signupEdtUsername.getText().toString(),
                                    binding.signupEdtPassword.getText().toString(),
                                    binding.signupEdtEmail.getText().toString());

                            String userId = task.getResult().getUser().getUid();
                            database.getReference().child(Constraints.USER_NODE).child(userId).setValue(user);

                            Toast.makeText(SignUpActivity.this, "User Created successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.signupTxtAlreadyHaveAccount.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, SignInActivity.class)));
    }
}