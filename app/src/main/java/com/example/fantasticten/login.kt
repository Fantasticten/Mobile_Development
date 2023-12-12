package com.example.fantasticten

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.fantasticten.data.LoginBody
import com.example.fantasticten.data.ValidateEmailBody
import com.example.fantasticten.databinding.ActivityLoginBinding
import com.example.fantasticten.repository.AuthRepository
import com.example.fantasticten.repository.UserRepository
import com.example.fantasticten.utils.APIService
import com.example.fantasticten.view_model.LoginActivityViewModel
import com.example.fantasticten.view_model.LoginActivityViewModelFactory
import com.example.fantasticten.view_model.UserViewModel
import java.lang.StringBuilder

class login : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginActivityViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.masukAkun.setOnClickListener(this)
        mBinding.editTextEmail.onFocusChangeListener = this
        mBinding.editTextEmail.onFocusChangeListener = this
        mBinding.editTextEmail.setOnKeyListener(this)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mViewModel = ViewModelProvider(
            this,
            LoginActivityViewModelFactory(AuthRepository(APIService.getService()), application)
        ).get(LoginActivityViewModel::class.java)

        setUpObservers()

        findViewById<TextView>(R.id.daftarLog).setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        findViewById<TextView>(R.id.lupaSan).setOnClickListener {
            startActivity(Intent(this, Lupa_Sandi::class.java))
        }

        mViewModel.getUser().observe(this) { user ->
            user?.let {
                val coba = "Username: ${it.username}, Email: ${it.email}"
                Log.d("LoginActivity", "Data from API: $coba")
                Log.d("LoginActivity", "id: ${it.id}")
            }
        }

    }

    private fun setUpObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }


        mViewModel.getErrorMessage().observe(this) {
            val formErrorKeys = arrayOf("username", "email", "password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {
                        "email" -> {
                            mBinding.textInputLayoutEmail.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "password" -> {
                            mBinding.textInputLayoutSandi.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                    }
                } else {
                    message.append(entry.value).append("\n")
                }

                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("INFORMATION")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }
                        .show()
                }
            }
        }

        mViewModel.getUser().observe(this) { user ->
            if (user != null) {
                userViewModel.setUser(user)

                saveLoginDataToSharedPreferences(
                    user.id,
                    user.username,
                    user.email,
                    user.phone_number,
                    "<token_here>"
                )

                saveLoginStatus(true)

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.d("LoginActivity", "User data is null")
            }
        }
    }

    private fun saveLoginDataToSharedPreferences(userId: Int, username: String, email: String, phoneNumber: String, token: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", userId)
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("phone_number", phoneNumber)
        editor.putString("token", token)
        editor.apply()
    }

    private fun saveLoginStatus(status: Boolean) {
        val sharedPreferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", status)
        editor.apply()
    }

    private fun validateEmail(shouldUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.editTextEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Email address is invalid"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.textInputLayoutEmail.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePassword(shouldUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.editTextSandi.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be 6 characters long"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.textInputLayoutSandi.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!validateEmail()) isValid = false
        if (!validatePassword()) isValid = false

        return isValid
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id) {
                R.id.masukAkun -> {
                    sumbitForm()
                }
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.editTextEmail -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutEmail.isErrorEnabled) {
                            mBinding.textInputLayoutEmail.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }

                R.id.editTextSandi -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutSandi.isErrorEnabled) {
                            mBinding.textInputLayoutSandi.isErrorEnabled = false
                        }
                    } else {
                        validatePassword()
                    }
                }
            }
        }
    }

    private fun sumbitForm() {
        if (validate()) {
            mViewModel.loginUser(LoginBody(
                mBinding.editTextEmail.text.toString(),
                mBinding.editTextSandi.text.toString()
            ))
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        if (event == KeyEvent.KEYCODE_ENTER && keyEvent?.action == KeyEvent.ACTION_UP) {
            sumbitForm()
        }
        return false
    }
}

