package com.example.fantasticten

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.fantasticten.data.RegisterBody
import com.example.fantasticten.data.ValidateEmailBody
import com.example.fantasticten.databinding.ActivityRegisterBinding
import com.example.fantasticten.fragment.HomeFragment
import com.example.fantasticten.repository.AuthRepository
import com.example.fantasticten.utils.APIService
import com.example.fantasticten.view_model.RegisterActivityViewModel
import com.example.fantasticten.view_model.RegisterActivityViewModelFactory
import java.lang.StringBuilder

class Register : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.editTextNamaLengkap.onFocusChangeListener = this
        mBinding.editTextEmail.onFocusChangeListener = this
        mBinding.editTextNoTlp.onFocusChangeListener = this
        mBinding.editTextSandi.onFocusChangeListener = this
        mBinding.editTextSandiRef.onFocusChangeListener = this
        mBinding.editTextSandiRef.setOnKeyListener(this)
        mBinding.editTextSandiRef.addTextChangedListener(this)
        mBinding.daftarGo.setOnClickListener(this)
        mViewModel = ViewModelProvider(this, RegisterActivityViewModelFactory(AuthRepository(APIService.getService()), application)).get(RegisterActivityViewModel::class.java)
        setUpObservers()

        findViewById<TextView>(R.id.masukLog).setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }

    }

    private fun setUpObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }

        mViewModel.getIsUniqueEmail().observe(this) {
            if (validateEmail(shouldUpdateView = false)) {
                if (it) {
                    mBinding.textInputLayoutEmail.apply {
                        if (isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                } else {
                    mBinding.textInputLayoutEmail.apply {
                        if (startIconDrawable != null) startIconDrawable = null
                        isErrorEnabled = true
                        error = "Email is Already taken"
                    }

                }
            }
        }

        mViewModel.getErrorMessage().observe(this) {
            val  formErrorKeys = arrayOf("username", "email", "password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when(entry.key) {
                        "username" -> {
                            mBinding.textInputLayoutNamaLengkap.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
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
                    mBinding.textInputLayoutEmail.apply {
                        if (isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
//                    AlertDialog.Builder(this)
//                        .setIcon(R.drawable.info_24)
//                        .setTitle("INFORMATION")
//                        .setMessage(message)
//                        .setPositiveButton("OK") {dialog, _ -> dialog!!.dismiss()}
//                        .show()
                }
            }
        }

        mViewModel.getUser().observe(this) {
            if (it != null) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun validateFullName(): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.editTextNamaLengkap.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Nama lengkap wajib di isi"
        }

        if (errorMessage != null) {
            mBinding.textInputLayoutNamaLengkap.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
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

    private fun validateNoHp(): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.editTextNoTlp.text.toString()
        if (value.isEmpty()) {
            errorMessage = "No Hp is required"
        }

        if (errorMessage != null) {
            mBinding.textInputLayoutNoTlp.apply {
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

    private fun validateConfirmPassword(shouldUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.editTextSandiRef.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"
        } else if (value.length < 6) {
            errorMessage = "Confirm Password must be 6 characters long"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.textInputLayoutSandiRef.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(shouldUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val password = mBinding.editTextSandi.text.toString()
        val confirmPassword = mBinding.editTextSandiRef.text.toString()
        if (password != confirmPassword) {
            errorMessage = "Confirm password doesn't match with password"
        }

        if (errorMessage != null && shouldUpdateView) {
            mBinding.textInputLayoutSandiRef.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.daftarGo)
            onSubmit()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when(view.id) {
                R.id.editTextNamaLengkap -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutNamaLengkap.isErrorEnabled) {
                            mBinding.textInputLayoutNamaLengkap.isErrorEnabled = false
                        }
                    } else {
                        validateFullName()
                    }
                }
                R.id.editTextEmail -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutEmail.isErrorEnabled) {
                            mBinding.textInputLayoutEmail.isErrorEnabled = false
                        }
                    } else {
                        if (validateEmail()) {
                            mViewModel.validateEmailAddress(ValidateEmailBody(mBinding.editTextEmail.text!!.toString()))
                        }
                    }
                }
                R.id.editTextNoTlp -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutNoTlp.isErrorEnabled) {
                            mBinding.textInputLayoutNoTlp.isErrorEnabled = false
                        }
                    } else {
                        validateNoHp()
                    }
                }
                R.id.editTextSandi -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutSandi.isErrorEnabled) {
                            mBinding.textInputLayoutSandi.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && mBinding.editTextSandiRef.text!!.isNotEmpty() && validateConfirmPassword() &&
                            validatePasswordAndConfirmPassword()) {
                            if (mBinding.textInputLayoutSandiRef.isErrorEnabled) {
                                mBinding.textInputLayoutSandiRef.isErrorEnabled = false
                            }
                            mBinding.textInputLayoutSandiRef.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
                R.id.editTextSandiRef -> {
                    if (hasFocus) {
                        if (mBinding.textInputLayoutSandiRef.isErrorEnabled) {
                            mBinding.textInputLayoutSandiRef.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (mBinding.textInputLayoutSandi.isErrorEnabled) {
                                mBinding.textInputLayoutSandi.isErrorEnabled = false
                            }
                            mBinding.textInputLayoutSandiRef.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP) {
            onSubmit()
        }
        return false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (validatePassword(shouldUpdateView = false) && validateConfirmPassword(shouldUpdateView = false) && validatePasswordAndConfirmPassword(shouldUpdateView = false)) {
            mBinding.textInputLayoutSandiRef.apply {
                if (isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.check_circle_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        } else {
            if (mBinding.textInputLayoutSandiRef.startIconDrawable != null)
                mBinding.textInputLayoutSandiRef.startIconDrawable = null
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun onSubmit() {
        if (validate()) {
            mViewModel.registerUser(RegisterBody(mBinding.editTextNamaLengkap.text!!.toString(), mBinding.editTextEmail.text!!.toString(), mBinding.editTextNoTlp.text!!.toString(), mBinding.editTextSandi.text!!.toString()))
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (!validateFullName()) isValid = false
        if (!validateEmail()) isValid = false
        if (!validateNoHp()) isValid = false
        if (!validatePassword()) isValid = false
        if (!validateConfirmPassword()) isValid = false
        if (isValid && !validatePasswordAndConfirmPassword()) isValid = false
        return isValid
    }
}
