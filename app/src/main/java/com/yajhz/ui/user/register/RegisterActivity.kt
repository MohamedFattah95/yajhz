package com.yajhz.ui.user.register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yajhz.R
import com.yajhz.data.model.api.DataWrapperModel
import com.yajhz.databinding.ActivityRegisterBinding
import com.yajhz.di.component.ActivityComponent
import com.yajhz.ui.base.BaseActivity
import com.yajhz.ui.main.MainActivity
import com.yajhz.utils.CommonUtils
import com.yajhz.utils.ErrorHandlingUtils
import retrofit2.HttpException

class RegisterActivity : BaseActivity<RegisterViewModel>(), RegisterNavigator {

    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel.setNavigator(this)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

        setUp()

    }

    private fun setUp() {

        initOnClick()
        subscribeViewModel()
    }

    private fun initOnClick() {

        binding.tvLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {

            if (binding.etName.text.toString().trim().isEmpty()) {
                binding.etName.error = getText(R.string.empty_full_name)
                binding.etName.requestFocus()
                //                    showMessage(R.string.invalid_email);
                return@setOnClickListener
            }
            binding.etName.error = null

            if (binding.etEmail.text.toString().trim().isEmpty() ||
                !CommonUtils.isEmailValid(binding.etEmail.text.toString().trim())
            ) {
                binding.etEmail.error = getText(R.string.invalid_email)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (!CommonUtils.isEmailValid(binding.etEmail.text.toString().trim())) {
                binding.etEmail.error = getText(R.string.invalid_email)
                binding.etEmail.requestFocus()
                //                    showMessage(R.string.invalid_email);
                return@setOnClickListener
            }
            binding.etEmail.error = null


            if (binding.etMobile.text.toString().trim().isEmpty()) {
                binding.etMobile.error = getText(R.string.empty_mobile)
                binding.etMobile.requestFocus()
                return@setOnClickListener
            }

            if (binding.etMobile.text.toString().trim().length != 11) {
                binding.etMobile.error = getText(R.string.invalid_mobile)
                binding.etMobile.requestFocus()
                return@setOnClickListener
            }
            binding.etMobile.error = null

            if (binding.etPassword.text.toString().trim().isEmpty()) {
                binding.etPassword.error = getText(R.string.empty_password)
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            if (binding.etPassword.text.toString().trim().length < 8) {
                binding.etPassword.error = getText(R.string.invalid_password)
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            if (binding.etPassword.text.toString().trim() !=
                binding.etConfirmPassword.text.toString().trim()
            ) {
                binding.etPassword.error = getText(R.string.passwords_not_matched)
                binding.etConfirmPassword.error = getText(R.string.passwords_not_matched)
                binding.etConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            binding.etPassword.error = null
            binding.etConfirmPassword.error = null

            val map: HashMap<String, String> = HashMap()

            map["name"] = binding.etName.text.toString().trim()
            map["email"] = binding.etEmail.text.toString().trim()
            map["phone"] = binding.etMobile.text.toString().trim()
            map["password"] = binding.etPassword.text.toString().trim()
            map["device_token"] = ""

            showLoading()
            mViewModel.doRegistration(map)

        }

    }

    private fun subscribeViewModel() {
        mViewModel.registerLiveData.observe(this) {
            hideLoading()
            startActivity(
                getIntentWithClearHistory(MainActivity::class.java)
            )
        }
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun handleError(throwable: Throwable) {
        hideLoading()
        if (throwable is HttpException) {

            try {
                val gson = Gson()
                val type = object : TypeToken<DataWrapperModel<Void>>() {}.type
                val errorResponse: DataWrapperModel<Void>? =
                    gson.fromJson(throwable.response()?.errorBody()?.charStream(), type)

                showErrorMessage(errorResponse?.message)
            } catch (e: Exception) {
                ErrorHandlingUtils.handleErrors(throwable)
            }

        } else {
            ErrorHandlingUtils.handleErrors(throwable)
        }
    }

    override fun showMyApiMessage(message: String?) {
        hideLoading()
        showErrorMessage(message)
    }

}
