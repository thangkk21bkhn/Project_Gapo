package com.example.shopeeshop.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.shopeeshop.MainActivity
import com.example.shopeeshop.R

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val process = view.findViewById<Button>(R.id.btn_payment)
        process.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).addFragment(OrderFragment())
        })
    }
}