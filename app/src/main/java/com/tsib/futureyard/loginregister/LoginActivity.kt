package com.tsib.futureyard.loginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tsib.futureyard.Constants
import com.tsib.futureyard.R

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d(Constants.TAG, "${Constants.LOGIN} onCreate()")

        supportFragmentManager
            .beginTransaction()
            .add(R.id.loginRegisterContainer, LoginFragment())
            //.add(R.id.LoginRegisterContainer, RegisterFragment())
            .commit()

    }


}