package com.example.famfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class GuardFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_guard, container, false)

        val location : CardView = view.findViewById(R.id.greenCard)
        location.setOnClickListener {
            val intent = Intent(requireContext(), LocationActivity::class.java)
            startActivity(intent)
        }

        val emailActivity : CardView = view.findViewById(R.id.redCard)
        emailActivity.setOnClickListener{
            val intent = Intent(requireContext(), SendEmailActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = GuardFragment()
    }
}