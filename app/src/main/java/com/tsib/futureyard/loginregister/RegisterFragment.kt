package com.tsib.futureyard.loginregister

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tsib.futureyard.Constants
import com.tsib.futureyard.R
import com.tsib.futureyard.main.MainActivity

class RegisterFragment : Fragment() {

    private lateinit var rootview: View

    private lateinit var et_fio: TextInputEditText
    private lateinit var et_email: TextInputEditText
    private lateinit var et_password: TextInputEditText
    private lateinit var et_adress: TextInputEditText

    private lateinit var btnRegister: MaterialButton
    private lateinit var btnToEnterPage: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(Constants.TAG, "${Constants.REGISTER} onCreateView()")

        rootview = inflater.inflate(R.layout.fragment_register, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(Constants.TAG, "${Constants.REGISTER} onViewCreated()")

        initViews()
        initListeners()

    }

    private fun initViews() {

        Log.d(Constants.TAG, "${Constants.LOGIN} initViews()")

        et_fio = rootview.findViewById(R.id.register_fio)
        et_email = rootview.findViewById(R.id.register_email)
        et_password = rootview.findViewById(R.id.register_password)
        et_adress = rootview.findViewById(R.id.register_adress)
        btnRegister = rootview.findViewById(R.id.btn_register)
        btnToEnterPage = rootview.findViewById(R.id.btn_to_enter)
    }

    private fun initListeners() {

        Log.d(Constants.TAG, "${Constants.LOGIN} initListeners()")

        btnRegister.setOnClickListener {
            registerUser()
        }
        btnToEnterPage.setOnClickListener {
            toEnterPage()
        }
    }

    private fun registerUser() {
        val fio = et_fio.text.toString()
        val email = et_email.text.toString()
        val pwd = et_password.text.toString()
        val adress = et_adress.text.toString()
        when {
            fio.isEmpty() -> {
                Log.d("CHECKER", "RegistrationActivity: Please enter email.")
                et_fio.error = "Please enter email."
                et_fio.requestFocus()
            }
            email.isEmpty() -> {
                Log.d("CHECKER", "RegistrationActivity: Please enter email.")
                et_email.error = "Please enter email."
                et_email.requestFocus()
            }
            pwd.isEmpty() -> {
                Log.d("CHECKER", "RegistrationActivity: Please enter password.")
                et_email.error = "Please enter password."
                et_email.requestFocus()
            }
            adress.isEmpty() -> {
                Log.d("CHECKER", "RegistrationActivity: Please enter name.")
                et_adress.error = "Please enter name."
                et_adress.requestFocus()
            }
            pwd.length < 6 -> {
                Log.d("CHECKER", "RegistrationActivity: Password must be at least 6 characters.")
                et_password.error = "Password must be at least 6 characters."
                et_password.requestFocus()
            }
            !(email.isEmpty() && pwd.isEmpty() && fio.isEmpty() && adress.isEmpty()) -> {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pwd)
                    .addOnFailureListener {
                        Log.d(
                            "CHECKER",
                            "RegistrationActivity: Failed to create user. ${it.message}"
                        )
                        Log.d("CHECKER", "RegistrationActivity: Name: $fio")
                        Log.d("CHECKER", "RegistrationActivity: Email: $email")
                        Log.d("CHECKER", "RegistrationActivity: Password: $pwd")
                        Toast.makeText(
                            activity,
                            "Failed to create user: ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Log.d("CHECKER", "RegistrationActivity: Failed to create user.")
                            Log.d("CHECKER", "RegistrationActivity: Name: $fio")
                            Log.d("CHECKER", "RegistrationActivity: Email: $email")
                            Log.d("CHECKER", "RegistrationActivity: Password: $pwd")
                            Toast.makeText(
                                activity,
                                "Failed to create user.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else { // else if successful
                            Log.d(
                                "CHECKER",
                                "RegistrationActivity: Successfully created user with uid: " +
                                        "${it.result?.user?.uid}"
                            )
                            Log.d("CHECKER", "RegistrationActivity: Name: $fio")
                            Log.d("CHECKER", "RegistrationActivity: Email: $email")
                            Log.d("CHECKER", "RegistrationActivity: Password: $pwd")

                            saveUserToDatabase(fio, email, adress)

                            startActivity(
                                Intent(
                                    activity,
                                    MainActivity::class.java
                                )
                            )
                        }
                    }
            }
        }
    }

    private fun saveUserToDatabase(fio: String, email: String, adress:String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(fio, email, adress, uid)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("CHECKER", "RegistrationActivity: User saved in Firebase.")
            }
            .addOnFailureListener {
                Log.d("CHECKER", "RegistrationActivity: Failed to save user in Firebase.")
            }
    }

    private fun toEnterPage() {

        Log.d(Constants.TAG, "${Constants.LOGIN} toRegisterPage()")

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.loginRegisterContainer, LoginFragment())
            .commit()
    }
}