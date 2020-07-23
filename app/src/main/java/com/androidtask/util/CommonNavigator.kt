package com.androidtask.util




interface CommonNavigator {

    fun init()

    fun showProgress()

    fun hideProgress()

    fun getStringResource(id: Int): String

    fun getIntegerResource(id: Int): Int

    fun setStatusBarColor() {}


}
