package com.ad.whatsappclone.ui.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ad.whatsappclone.databinding.ActivitySignUpBinding
import com.ad.whatsappclone.models.Constraints
import com.ad.whatsappclone.models.Users
import com.ad.whatsappclone.ui.BaseActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var mAuth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        progressDialog = ProgressDialog(this@SignUpActivity)
        progressDialog!!.setTitle("Creating Account")
        progressDialog!!.setMessage("We are creating your account")
        binding.signupBtnSignup.setOnClickListener { view: View? ->
            progressDialog!!.show()
            mAuth!!.createUserWithEmailAndPassword(
                binding.signupEdtEmail.text.toString(),
                binding.signupEdtPassword.text.toString()
            )
                .addOnCompleteListener { task: Task<AuthResult> ->
                    progressDialog!!.dismiss()
                    if (task.isSuccessful) {
                        val user = Users(
                            binding.signupEdtUsername.text.toString(),
                            binding.signupEdtPassword.text.toString(),
                            binding.signupEdtEmail.text.toString()
                        )
                        val userId = Objects.requireNonNull(task.result.user)?.uid
                        if (userId != null) {
                            database!!.reference.child(Constraints.USER_NODE).child(userId)
                                .setValue(user)
                        }

                        Toast.makeText(
                            this@SignUpActivity,
                            "User Created successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        binding.signupTxtAlreadyHaveAccount.setOnClickListener {
            startActivity(
                Intent(this@SignUpActivity, SignInActivity::class.java)
            )
        }
    }
}