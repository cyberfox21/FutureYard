package com.tsib.futureyard.loginregister

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tsib.futureyard.Constants
import com.tsib.futureyard.R
import com.tsib.futureyard.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var rootview: View
    private lateinit var etLogin: TextInputEditText
    private lateinit var etPassword: TextInputEditText
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

        val login = etLogin.text.toString()
        val pwd = etPassword.text.toString()
        if (login.isEmpty()) {
            Log.d("CHECKER", "LoginActivity: Please enter email.")
            etLogin.error = "Please enter email."
            etLogin.requestFocus()
        } else if (pwd.isEmpty()) {
            Log.d("CHECKER", "LoginActivity: Please enter password.")
            etPassword.error = "Please enter password."
            etPassword.requestFocus()
        } else if (pwd.length < 6) {
            Log.d("CHECKER", "LoginActivity: Password must be at least 6 characters.")
            etLogin.error = "Password must be at least 6 characters."
            etLogin.requestFocus()
        } else if (login.isEmpty() && pwd.isEmpty()) {
            Log.d("CHECKER", "LoginActivity: Fields are empty!")
            Toast.makeText(activity, "Fields are empty!", Toast.LENGTH_SHORT).show()
        } else if (!(login.isEmpty() && pwd.isEmpty())) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(login, pwd)
                .addOnFailureListener {
                    Log.d("CHECKER", "LoginActivity: Failed: ${it.message}")
                    Toast.makeText(activity, "Failed: ${it.message}", Toast.LENGTH_LONG).show()
                }
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Log.d("CHECKER", "LoginActivity: Error occurred!")
                        Toast.makeText(activity, "Error occurred!", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    Log.d(
                        "CHECKER",
                        "LoginActivity: User logged in successfully with uid: ${it.result?.user?.uid}"
                    )
                    startActivity(Intent(activity, MainActivity::class.java))
                }
        }

    }

    private fun toRegisterPage() {

        Log.d(Constants.TAG, "${Constants.LOGIN} toRegisterPage()")

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.loginRegisterContainer, RegisterFragment())
            .commit()

    }
}