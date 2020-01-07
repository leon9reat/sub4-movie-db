package com.medialink.sub4moviedb.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.medialink.sub4moviedb.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvUserName: TextView = root.findViewById(R.id.tv_username)
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            img_profile.setImageResource(R.drawable.benk100)
            tvUserName.text = it.userName
            tv_followers_profile.text = it.countFollower.toString()
            tv_photos_profile.text = it.countPhotos.toString()
            tv_following_profile.text = it.countFollowing.toString()

            tv_email_profile.text = it.email
            tv_phone_profile.text = it.phone
        })
        return root
    }
}