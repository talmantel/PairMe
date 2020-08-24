package com.idealtap.pairme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.idealtap.pairme.R
import com.idealtap.pairme.data.model.Profile
import com.idealtap.pairme.util.InjectorUtils
import kotlinx.android.synthetic.main.main_fragment.*

class ProfilesFragment private constructor(): Fragment() {

    companion object {
        fun newInstance() = ProfilesFragment()
    }

    private val viewModel by viewModels<MainViewModel> {
        InjectorUtils.provideMainViewModelFactory()
    }

    private var profile1: Profile? = null
    private var profile2: Profile? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.suggestedProfiles.observe(viewLifecycleOwner, Observer{profilesResource ->
            if(profilesResource.isSuccessful && profilesResource.data()!!.size >= 2){
                val profiles = profilesResource.data()!!
                profile1 = profiles[0]
                profile2 = profiles[1]
                full_name_1.text = "${profile1!!.fname} ${profile1!!.lname}"
                full_name_2.text = "${profile2!!.fname} ${profile2!!.lname}"
            }
            else{
                Toast.makeText(requireActivity(), "Cant load more matches!", Toast.LENGTH_LONG).show()
            }
        })

        select_profile_1.setOnClickListener {
            viewModel.selectFromPair(profile1!!, profile2!!)
        }

        select_profile_2.setOnClickListener {
            viewModel.selectFromPair(profile2!!, profile1!!)
        }
    }
}