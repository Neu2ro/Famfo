package com.example.famfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class ProfileFragment : Fragment() {
    private lateinit var namevw : TextView
    private lateinit var phoneNovw : TextView
    private lateinit var emailvw : TextView
    private lateinit var userNamevw : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        namevw = view.findViewById(R.id.nameVW)
        phoneNovw = view.findViewById(R.id.phoneVW)
        emailvw = view.findViewById(R.id.emailVW)
        userNamevw = view.findViewById(R.id.usernameVW)

        val mname = activity?.intent?.getStringExtra("Name")
        val musername = activity?.intent?.getStringExtra("Username")
        val mphone = activity?.intent?.getStringExtra("Phone")
        val memail = activity?.intent?.getStringExtra("Email")

        namevw.setText(mname)
        phoneNovw.setText(mphone)
        emailvw.setText(memail)
        userNamevw.setText(musername)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}