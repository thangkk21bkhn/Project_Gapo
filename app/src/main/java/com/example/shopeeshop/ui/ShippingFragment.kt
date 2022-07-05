package com.example.shopeeshop.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.shopeeshop.MainActivity
import com.example.shopeeshop.R

class ShippingFragment : Fragment(R.layout.fragment_shipping) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnContinue = view.findViewById<Button>(R.id.btn_continue)
        btnContinue.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).addFragment(PaymentFragment())
        })
    }
}