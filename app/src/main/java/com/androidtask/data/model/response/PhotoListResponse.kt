package com.androidtask.data.model.response

import com.androidtask.data.model.bean.PhotoListBean
import com.androidtask.data.remote.Api
import com.androidtask.data.remote.ApiFactory
import com.androidtask.util.NetworkResponseCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class PhotoListResponse : BaseResponse<PhotoListResponse, String, Any>() {


    var photos: PhotoListBean? = null

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        networkResponseCallback: NetworkResponseCallback<PhotoListResponse>
    ): Disposable {
        val api = ApiFactory.clientWithoutHeader.create(Api::class.java)
        return api.photoListPI(
            requestParam
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { networkResponseCallback.onResponse(it) }
    }


}
