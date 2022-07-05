package com.example.shopeeshop.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shopeeshop.MainActivity
import com.example.shopeeshop.R
import com.example.shopeeshop.api.RetrofitShopee
import com.example.shopeeshop.model.LoginRequest
import com.example.shopeeshop.repository.ProductRepository
import com.example.shopeeshop.repository.SignInRepository
import com.example.shopeeshop.viewmodel.MainViewModel
import com.example.shopeeshop.viewmodel.MainViewModelFactory
import com.example.shopeeshop.viewmodel.SignInViewModel
import com.example.shopeeshop.viewmodel.SignInViewModelFactory
import kotlin.math.log

class SignupFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var viewModel: SignInViewModel
    private lateinit var error : TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email : EditText = view.findViewById(R.id.email)
        val passWord : EditText = view.findViewById(R.id.password)
        val signIn : Button = view.findViewById(R.id.sign_in)
        val register : TextView = view.findViewById(R.id.tv_register)
        error = view.findViewById(R.id.tv_incorrect_infor)
        val viewModelFactory = SignInViewModelFactory(SignInRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)
        register.setOnClickListener(View.OnClickListener {
            this.activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, RegisterFragment())
                ?.addToBackStack(null)
                ?.commitAllowingStateLoss()
        })
        signIn.setOnClickListener(View.OnClickListener {
            val request = LoginRequest(email.text.toString(), passWord.text.toString())
            viewModel.login(request)
        })

        viewModel.data.observe(viewLifecycleOwner, { res ->
            val result = res.body()
            if (result != null) {
                backToMain(result.name)
            }
            else
                showError()
        })
    }

    private fun showError() {
        error.visibility = View.VISIBLE
    }

    private fun backToMain(name : String) {
        for (i in 0..(this.activity?.supportFragmentManager?.backStackEntryCount?.minus(1) ?: 0)) {
            this.activity?.supportFragmentManager?.popBackStack()
        }
        (activity as MainActivity?)!!.resetData(name)
    }
}