package com.androidtask.ui.base


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel<N : Any> : ViewModel() {

    private val isLoading = ObservableBoolean(false)
    protected val disposable = CompositeDisposable()

    var navigator: N? = null

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }


}
