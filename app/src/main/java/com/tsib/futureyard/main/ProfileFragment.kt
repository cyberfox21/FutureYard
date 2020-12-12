package com.tsib.futureyard.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tsib.futureyard.R
import com.tsib.futureyard.loginregister.User

class ProfileFragment : Fragment() {

    private lateinit var rootview: View

    private lateinit var userFio: com.google.android.material.textfield.TextInputEditText
    private lateinit var userEmail: com.google.android.material.textfield.TextInputEditText
    private lateinit var userPassword: com.google.android.material.textfield.TextInputEditText
    private lateinit var userAdress: com.google.android.material.textfield.TextInputEditText
    private lateinit var btnChangeUser: com.google.android.material.button.MaterialButton

    private var cardRef = FirebaseDatabase.getInstance().reference.child("notes").child(FirebaseAuth.getInstance().uid.toString())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        rootview = inflater.inflate(R.layout.fragment_profile, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields() // инициализируем views
        initListeners() // инициализируем слушатели событий

    }

    private fun initFields() {
        userFio = rootview.findViewById<TextInputEditText>(R.id.profil_fio)
        userEmail = rootview.findViewById<TextInputEditText>(R.id.profil_email)
        userPassword = rootview.findViewById<TextInputEditText>(R.id.profil_password)
        userAdress = rootview.findViewById<TextInputEditText>(R.id.profil_adress)
        btnChangeUser = rootview.findViewById(R.id.btn_change)

        setUserFields()
    }

    private fun setUserFields() {
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue<User>(User::class.java)
                userFio.setText(user!!.fio)
                userEmail.setText(user!!.email)
                userPassword.setText(user!!.password)
                userAdress.setText(user!!.adress)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        if(FirebaseAuth.getInstance().currentUser != null){
            FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(menuListener)
        }
    }

    private fun initListeners() {
        btnChangeUser.setOnClickListener {
            changeUser()
        }
    }

    private fun changeUser() {

        val model = User(
            userFio.text.toString() ?: "",
            userEmail.text.toString() ?: "",
            userPassword.text.toString() ?: "",
            userAdress.text.toString() ?: "",
            FirebaseAuth.getInstance().currentUser.toString() ?: ""
        )

//        Log.d(
//            "CHECKER",
//            "PasswordCard Added" +
//                    "Service: ${model.service} | Login: ${model.login} | Password: ${model.password} | Path: ${model.path}"
//        )

        FirebaseAuth.getInstance().currentUser?.let {
            FirebaseDatabase.getInstance().reference.child("users").child(it.uid).setValue(
                model
            )
        }

    }


}