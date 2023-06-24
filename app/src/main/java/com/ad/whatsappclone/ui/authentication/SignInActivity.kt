package com.ad.whatsappclone.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ad.whatsappclone.R
import com.ad.whatsappclone.databinding.ActivitySignInBinding
import com.ad.whatsappclone.models.Constraints
import com.ad.whatsappclone.models.Users
import com.ad.whatsappclone.ui.BaseActivity
import com.ad.whatsappclone.ui.home.HomeActivity
import com.ad.whatsappclone.util.widget.AdProgressDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var database: FirebaseDatabase? = null
    var progressDialog: AdProgressDialog? = null
    var RC_SIGN_IN = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        progressDialog = AdProgressDialog(this@SignInActivity)
        progressDialog?.setTitle("Login")
        progressDialog?.setMessage("Login your account")

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.signinTxtSignup.setOnClickListener {
            startActivity(
                Intent(
                    this@SignInActivity,
                    SignUpActivity::class.java
                )
            )
        }
        binding.signinBtnSignin.setOnClickListener {
            progressDialog?.show()
            mAuth!!.signInWithEmailAndPassword(
                binding.signinEdtEmail.text.toString(),
                binding.signinEdtPassword.text.toString()
            )
                .addOnCompleteListener { task ->
                    progressDialog?.dismiss()
                    if (task.isSuccessful) {
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        binding.signinBtnGoogle.setOnClickListener { signIn() }
        if (mAuth?.currentUser != null) {
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val firebaseUser = mAuth!!.currentUser
                    val user = Users()
                    user.userId = firebaseUser!!.uid
                    user.userName = firebaseUser.displayName
                    user.userProfilePic = firebaseUser.photoUrl.toString()
                    database!!.reference.child(Constraints.USER_NODE).child(
                        firebaseUser.uid
                    ).setValue(user)
                    //       updateUI(user);
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    Toast.makeText(
                        this@SignInActivity,
                        "Successfully sign in with google",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    //                            Snackbar.make(binding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                    //   updateUI(null);
                }

                // ...
            }
    }
}