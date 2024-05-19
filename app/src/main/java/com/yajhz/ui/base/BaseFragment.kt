package com.yajhz.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yajhz.MvvmApp
import com.yajhz.di.component.DaggerFragmentComponent
import com.yajhz.di.component.FragmentComponent
import com.yajhz.di.module.FragmentModule
import com.yajhz.utils.CommonUtils
import javax.inject.Inject

abstract class BaseFragment<V : BaseViewModel<*>> : Fragment() {
    private var mActivity: BaseActivity<*>? = null
    private val mRootView: View? = null
    lateinit var mProgressDialog: ProgressDialog

    @Inject
    lateinit var mViewModel: V

    companion object {
        const val ARGS_INSTANCE = "com.qrc.CSC.argsInstance"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            val activity: BaseActivity<*> = context
            mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mRootView
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    val baseActivity: BaseActivity<*>
        get() = this.mActivity!!

    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    fun showKeyboard() {
        if (mActivity != null) {
            mActivity!!.showKeyboard()
        }
    }

    val isNetworkConnected: Boolean
        get() = mActivity != null && mActivity!!.isNetworkConnected

    fun openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity!!.openActivityOnTokenExpire()
        }
    }


    open fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(mActivity)
    }

    open fun hideLoading() {
        if (::mProgressDialog.isInitialized) {
            mProgressDialog.dismiss()
        }
    }


    abstract fun performDependencyInjection(buildComponent: FragmentComponent?)

    private val buildComponent: FragmentComponent
        get() = DaggerFragmentComponent.builder()
            .appComponent(((requireContext().applicationContext) as MvvmApp).appComponent)
            .fragmentModule(FragmentModule(this))
            .build()

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }

    protected open fun showMessage(m: String?) {
        mActivity?.showMessage(m)
    }

    protected open fun showMessage(id: Int) {
        mActivity?.showMessage(id)
    }

    protected open fun showErrorMessage(m: String?) {
        mActivity?.showErrorMessage(m)
    }

    protected open fun showSuccessMessage(m: String?) {
        mActivity?.showSuccessMessage(m)
    }

}
