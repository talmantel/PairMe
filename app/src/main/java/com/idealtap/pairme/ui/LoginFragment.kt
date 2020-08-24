package com.idealtap.pairme.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.idealtap.pairme.R
import com.idealtap.pairme.util.InjectorUtils
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment private constructor(): Fragment() {

    companion object {
        const val RC_GOOGLE_SIGN_IN = 123

        fun newInstance() = LoginFragment()
    }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val viewModel by viewModels<LoginViewModel> {
        InjectorUtils.provideLoginViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())

        if(account != null) {
            progress_loader.visibility = View.VISIBLE
            viewModel.loginWithGoogle(account.idToken!!).observe(viewLifecycleOwner, Observer {resource ->
                if(resource != null && !resource.isSuccessful){
                    progress_loader.visibility = View.GONE
                }
            })
        }

        google_sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        google_sign_in_button.setOnClickListener{signInWithGoogle()}
    }

    private fun signInWithGoogle(){
        startActivityForResult( mGoogleSignInClient.signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("pairme", "firebaseAuthWithGoogle:" + account.id)
                viewModel.loginWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("pairme", "Google sign in failed", e)
            }
        }
    }
}