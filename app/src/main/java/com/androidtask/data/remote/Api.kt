package com.androidtask.data.remote


import com.androidtask.data.model.response.PhotoListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface Api {

    @GET("rest/")
    fun photoListPI(
        @QueryMap map: Map<String, @JvmSuppressWildcards Any>
    ): Observable<PhotoListResponse>


}