package com.androidtask.data.model.response

import com.androidtask.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BaseResponse<T, K, V> {

    @SerializedName("stat")
    var isSuccess: String = ""

    @SerializedName("message")
    var message: String = ""

    abstract fun doNetworkRequest(
        requestParam: HashMap<K, V>,
        networkResponseCallback: NetworkResponseCallback<T>
    ): Disposable
}
