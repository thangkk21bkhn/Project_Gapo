package com.example.shopeeshop.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shopeeshop.MainActivity
import com.example.shopeeshop.R
import com.example.shopeeshop.model.SignUpRequest
import com.example.shopeeshop.repository.SignInRepository
import com.example.shopeeshop.repository.SignUpRepository
import com.example.shopeeshop.viewmodel.SignInViewModel
import com.example.shopeeshop.viewmodel.SignInViewModelFactory
import com.example.shopeeshop.viewmodel.SignUpViewModel
import com.example.shopeeshop.viewmodel.SignUpViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var wrongPassword : EditText
    private lateinit var userAlreadyExists : EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login : TextView = view.findViewById(R.id.tv_sign_in)
        val name : EditText = view.findViewById(R.id.name)
        val email : EditText = view.findViewById(R.id.email)
        val password : EditText = view.findViewById(R.id.password)
        val confirmPassword : EditText = view.findViewById(R.id.confirm_password)

        wrongPassword = view.findViewById(R.id.tv_wrong_password)
        userAlreadyExists = view.findViewById(R.id.tv_user_already)
        val viewModelFactory = SignUpViewModelFactory(SignUpRepository())
        val signUp : Button = view.findViewById(R.id.sign_up)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)
        login.setOnClickListener(View.OnClickListener {
            this.activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, SignupFragment())
                ?.addToBackStack(null)
                ?.commitAllowingStateLoss()
        })

        signUp.setOnClickListener(View.OnClickListener {
            if (!(confirmPassword.text.toString().equals(password.text.toString())))
                wrongPassword.visibility = View.VISIBLE
            else
                viewModel.signUp(SignUpRequest(name.text.toString(),email.text.toString(), password.text.toString()))
        })

        viewModel.data.observe(viewLifecycleOwner, { res ->
            val result = res.body()
            if (result != null)
                backToMain(result.name)
            else
                showError()
        })
    }

    private fun showError() {
        userAlreadyExists.visibility = View.VISIBLE
    }

    private fun backToMain(name : String) {
        for (i in 0..(this.activity?.supportFragmentManager?.backStackEntryCount?.minus(1) ?: 0)) {
            this.activity?.supportFragmentManager?.popBackStack()
        }
        (activity as MainActivity?)!!.resetData(name)
    }
}