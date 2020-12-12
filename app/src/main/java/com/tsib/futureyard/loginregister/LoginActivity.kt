package com.tsib.futureyard.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.tsib.futureyard.Constants
import com.tsib.futureyard.R
import com.tsib.futureyard.main.MainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d(Constants.TAG, "${Constants.LOGIN} onCreate()")

        if (FirebaseAuth.getInstance().currentUser != null) {

            val startIntent = Intent((applicationContext), MainActivity::class.java)
            startActivity(startIntent)

        } else {

            supportFragmentManager
                .beginTransaction()
                .add(R.id.loginRegisterContainer, LoginFragment())
                .commit()

        }

    }


}