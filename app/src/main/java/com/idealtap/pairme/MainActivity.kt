package com.idealtap.pairme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.idealtap.pairme.ui.LoginFragment
import com.idealtap.pairme.ui.LoginViewModel
import com.idealtap.pairme.ui.ProfilesFragment
import com.idealtap.pairme.util.InjectorUtils


class MainActivity : AppCompatActivity() {

    private val profilesFragment by lazy { ProfilesFragment.newInstance() }
    private val loginFragment by lazy { LoginFragment.newInstance() }

    private val viewModel by viewModels<LoginViewModel> {
        InjectorUtils.provideLoginViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.loggedInUser.observe(this, Observer { userResource ->
            if (userResource.isSuccessful) {
                if (userResource.data() != null) {
                    Toast.makeText(this, "Hello ${userResource.data()!!.displayName}!", Toast.LENGTH_LONG).show()

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.container, profilesFragment)
                        .commitNow()

                } else {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.container, loginFragment, "loginFragment")
                        .commitNow()
                }
            } else {
                Toast.makeText(this, "An error occurred trying to check login", Toast.LENGTH_LONG)
                    .show()
                userResource.error().printStackTrace()
            }
        })
    }
}
