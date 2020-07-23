package com.androidtask.ui.base


import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.androidtask.R
import com.androidtask.util.CommonNavigator
import com.androidtask.util.ProgressHUD
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    CommonNavigator {

    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    var compositeDisposable = CompositeDisposable()
    private var pDialog: ProgressDialog? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        viewDataBinding!!.lifecycleOwner = this


    }


    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }



    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    override fun getStringResource(id: Int): String {
        return resources.getString(id)
    }

    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }


    fun checkIfInternetOn(): Boolean {
        if (isInternetOn(this)) {
            return true
        } else {
            showMessage(
                getStringResource(R.string.validation_internet_connection),
                this
            )
            return false
        }
    }

    private fun showMessage(message: String, context: Context) {

        try {
            val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            val toastView = toast.view
            val toastMessage = toastView!!.findViewById<TextView>(android.R.id.message)
            toastMessage.setTextColor(Color.WHITE)
            toastMessage.gravity = Gravity.CENTER
            toast.show()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    override fun showProgress() {
        if (pDialog != null) {
            pDialog!!.dismiss()
        } else {
        }
        pDialog = ProgressHUD.init(this@BaseActivity, false, false)
        pDialog!!.show()
    }

    override fun hideProgress() {
        if (pDialog != null) pDialog!!.dismiss()
        if (pDialog != null) pDialog!!.cancel()
    }

    private fun isInternetOn(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val networkInfo = connectivity.activeNetworkInfo
            return (networkInfo != null && networkInfo.isAvailable
                    && networkInfo.isConnected)
        }
        return false
    }


}

