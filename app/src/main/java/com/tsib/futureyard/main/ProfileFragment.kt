package com.tsib.futureyard.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.Scopes.PROFILE
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tsib.futureyard.Constants.TAG
import com.tsib.futureyard.R
import com.tsib.futureyard.loginregister.User

class ProfileFragment : Fragment() {

    private lateinit var rootview: View

    private lateinit var userFio: TextInputEditText
    private lateinit var userEmail: TextInputEditText
    private lateinit var userPassword: TextInputEditText
    private lateinit var userAdress: TextInputEditText
    private lateinit var btnChangeUser: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        Log.d(TAG, "$PROFILE onCreateView()")

        rootview = inflater.inflate(R.layout.fragment_profile, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "$PROFILE onViewCreated()")

        initFields() // инициализируем views
        initListeners() // инициализируем слушатели событий

    }

    private fun initFields() {

        Log.d(TAG, "$PROFILE initFields()")

        userFio = rootview.findViewById(R.id.profil_fio)
        userEmail = rootview.findViewById(R.id.profil_email)
        userPassword = rootview.findViewById(R.id.profil_password)
        userAdress = rootview.findViewById(R.id.profil_adress)
        btnChangeUser = rootview.findViewById(R.id.btn_change)

        setUserFields()
    }

    private fun setUserFields() {

        Log.d(TAG, "$PROFILE setUserFields()")

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                userFio.setText(user!!.fio)
                userEmail.setText(user.email)
                userPassword.setText(user.password)
                userAdress.setText(user.adress)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        if(FirebaseAuth.getInstance().currentUser != null){
            FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance()
                .currentUser!!.uid).addListenerForSingleValueEvent(menuListener)
        }
    }

    private fun initListeners() {

        Log.d(TAG, "$PROFILE initListeners()")

        btnChangeUser.setOnClickListener {
            changeUser()
        }
    }

    private fun changeUser() {

        Log.d(TAG, "$PROFILE changeUser()")

        val model = User(
            userFio.text.toString() ?: "",
            userEmail.text.toString() ?: "",
            userPassword.text.toString() ?: "",
            userAdress.text.toString() ?: "",
            FirebaseAuth.getInstance().currentUser.toString() ?: ""
        )

        FirebaseAuth.getInstance().currentUser?.let {
            FirebaseDatabase.getInstance().reference.child("users").child(it.uid).setValue(
                model
            )
        }

    }


}