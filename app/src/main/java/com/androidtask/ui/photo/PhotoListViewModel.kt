package com.androidtask.ui.photo

import com.androidtask.data.model.response.PhotoListResponse
import com.androidtask.ui.base.BaseViewModel
import com.androidtask.util.AppConstants
import com.androidtask.util.NetworkResponseCallback

import java.util.*

class PhotoListViewModel : BaseViewModel<PhotoListNavigator>() {

    private var requestParam: HashMap<String, Any>? = null

    fun init() {
        navigator!!.init()
    }

    internal fun callPhotoListAPI(
        showProgress: Boolean, page: String, searchValue: String
    ) {
        if (showProgress) {
            navigator!!.showPageLoader()
        } else {
            navigator!!.showProgress()
        }
        disposable.add(
            PhotoListResponse().doNetworkRequest(prepareRequestHashMap(page, searchValue),
                object : NetworkResponseCallback<PhotoListResponse> {
                    override fun onResponse(`object`: PhotoListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.showHideLoader()
                        if (`object`.isSuccess == "ok") {
                            navigator!!.getPhotoListResponse(`object`)
                        } else if (`object`.isSuccess == "fail") {

                            navigator!!.showerrormessage(`object`)
                        }
                    }


                })
        )
    }

    private fun prepareRequestHashMap(page: String, searchValue: String): HashMap<String, Any> {
        requestParam = HashMap()
        requestParam!!["method"] = AppConstants.METHOD
        requestParam!!["api_key"] = AppConstants.API_KEY
        requestParam!!["text"] = searchValue
        requestParam!!["format"] = AppConstants.FORMAT
        requestParam!!["nojsoncallback"] = AppConstants.JSON_CALL_BACK
        requestParam!!["per_page"] = AppConstants.PER_PAGE
        requestParam!!["page"] = page
        return requestParam!!
    }
}