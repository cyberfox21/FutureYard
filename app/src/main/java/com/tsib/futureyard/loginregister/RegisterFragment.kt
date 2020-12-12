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
import com.tsib.futureyard.Constants.REGISTER
import com.tsib.futureyard.Constants.TAG
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

        Log.d(TAG, "$REGISTER onCreateView()")

        rootview = inflater.inflate(R.layout.fragment_register, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "$REGISTER onViewCreated()")

        initViews()
        initListeners()

    }

    private fun initViews() {

        Log.d(TAG, "$REGISTER initViews()")

        et_fio = rootview.findViewById(R.id.register_fio)
        et_email = rootview.findViewById(R.id.register_email)
        et_password = rootview.findViewById(R.id.register_password)
        et_adress = rootview.findViewById(R.id.register_adress)
        btnRegister = rootview.findViewById(R.id.btn_register)
        btnToEnterPage = rootview.findViewById(R.id.btn_to_enter)
    }

    private fun initListeners() {

        Log.d(TAG, "$REGISTER initListeners()")

        btnRegister.setOnClickListener {
            registerUser()
        }
        btnToEnterPage.setOnClickListener {
            toEnterPage()
        }
    }

    private fun registerUser() {

        Log.d(TAG, "$REGISTER registerUser()")

        val fio = et_fio.text.toString()
        val email = et_email.text.toString()
        val pwd = et_password.text.toString()
        val adress = et_adress.text.toString()

        when {

            fio.isEmpty() -> {
                Log.d(TAG, "$REGISTER Please enter name.")
                et_fio.error = "Please enter name."
                et_fio.requestFocus()
            }

            email.isEmpty() -> {
                Log.d(TAG, "$REGISTER  Please enter email.")
                et_email.error = "Please enter email."
                et_email.requestFocus()
            }

            pwd.isEmpty() -> {
                Log.d(TAG, "$REGISTER  Please enter password.")
                et_email.error = "Please enter password."
                et_email.requestFocus()
            }

            adress.isEmpty() -> {
                Log.d(TAG, "$REGISTER  Please enter adress.")
                et_adress.error = "Please enter adress."
                et_adress.requestFocus()
            }

            pwd.length < 6 -> {
                Log.d(TAG, "$REGISTER  Password must be at least 6 characters.")
                et_password.error = "Password must be at least 6 characters."
                et_password.requestFocus()
            }

            !(email.isEmpty() && pwd.isEmpty() && fio.isEmpty() && adress.isEmpty()) -> {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pwd)
                    .addOnFailureListener {
                        Log.d(
                            TAG,
                            "$REGISTER  Failed to create user. ${it.message}"
                        )
                        Toast.makeText(
                            activity,
                            "Failed to create user: ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Log.d(TAG, "$REGISTER  Failed to create user.")
                            Toast.makeText(
                                activity,
                                "Failed to create user.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else { // else if successful
                            Log.d(
                                TAG,
                                "$REGISTER  Successfully created user with uid: " +
                                        "${it.result?.user?.uid}"
                            )

                            saveUserToDatabase(fio, email, adress, pwd)

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

    private fun saveUserToDatabase(fio: String, email: String, pwd: String, adress: String) {

        Log.d(TAG, "$REGISTER saveUserToDatabase()")

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(fio, email, adress, pwd, uid)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "$REGISTER  User saved in Firebase.")
            }
            .addOnFailureListener {
                Log.d(TAG, "$REGISTER  Failed to save user in Firebase.")
            }
    }

    private fun toEnterPage() {

        Log.d(TAG, "$REGISTER toRegisterPage()")

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.loginRegisterContainer, LoginFragment())
            .commit()
    }
}