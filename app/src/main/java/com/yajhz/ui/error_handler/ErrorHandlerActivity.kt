package com.yajhz.ui.error_handler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yajhz.databinding.ActivityErrorHandlerBinding
import com.yajhz.di.component.ActivityComponent
import com.yajhz.ui.base.BaseActivity
import com.yajhz.utils.ErrorHandlingUtils

class ErrorHandlerActivity : BaseActivity<ErrorHandlerViewModel>(), ErrorHandlerNavigator {

    lateinit var binding: ActivityErrorHandlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel.setNavigator(this)
        setUp()

    }

    private fun setUp() {

        initOnClick()
        subscribeViewModel()


    }

    private fun initOnClick() {

        binding.okBtn.setOnClickListener {
        }

    }

    private fun subscribeViewModel() {

    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, ErrorHandlerActivity::class.java)
        }
    }

    override fun handleError(throwable: Throwable) {
        hideLoading()
        ErrorHandlingUtils.handleErrors(throwable)
    }

    override fun showMyApiMessage(message: String?) {
        hideLoading()
        showErrorMessage(message)
    }

    override fun showUserDeletedMsg() {
        hideLoading()
//        binding.text.setText(R.string.your_account_deleted)
        binding.okBtn.visibility = View.VISIBLE
    }

}
