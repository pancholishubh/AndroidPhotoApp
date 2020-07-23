package com.androidtask.ui.photo

import com.androidtask.data.model.response.PhotoListResponse
import com.androidtask.util.CommonNavigator

interface PhotoListNavigator : CommonNavigator {

    fun clickOnBackButton()

    fun showPageLoader()

    fun showHideLoader()
    fun getPhotoListResponse(photoListResponse: PhotoListResponse)
    fun showerrormessage(photoListResponse: PhotoListResponse)

}