package com.tsib.futureyard.loginregister

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.tsib.futureyard.Constants
import com.tsib.futureyard.R

class LoginFragment : Fragment() {

    private lateinit var rootview: View
    private lateinit var etLogin: TextInputLayout
    private lateinit var etPassword: TextInputLayout
    private lateinit var btnEnter: MaterialButton
    private lateinit var btnToRegisterPage: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(Constants.TAG, "${Constants.LOGIN} onCreateView()")

        rootview = inflater.inflate(R.layout.fragment_login, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(Constants.TAG, "${Constants.LOGIN} onViewCreated()")

        initViews()
        initListeners()

    }

    private fun initViews() {

        Log.d(Constants.TAG, "${Constants.LOGIN} initViews()")

        etLogin = rootview.findViewById(R.id.et_login)
        etPassword = rootview.findViewById(R.id.et_password)
        btnEnter = rootview.findViewById(R.id.btn_enter)
        btnToRegisterPage = rootview.findViewById(R.id.btn_toregister)
    }

    private fun initListeners() {

        Log.d(Constants.TAG, "${Constants.LOGIN} initListeners()")

        btnEnter.setOnClickListener {
            enterUser()
        }
        btnToRegisterPage.setOnClickListener {
            toRegisterPage()
        }
    }

    private fun enterUser() {

        Log.d(Constants.TAG, "${Constants.LOGIN} loginUser()")



    }

    private fun toRegisterPage() {

        Log.d(Constants.TAG, "${Constants.LOGIN} toRegisterPage()")

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.loginRegisterContainer, RegisterFragment())
            .commit()

    }
}