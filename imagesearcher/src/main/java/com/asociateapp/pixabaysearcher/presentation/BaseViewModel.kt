package com.asociateapp.pixabaysearcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asociateapp.pixabaysearcher.utils.CoroutineExecutor
import kotlinx.coroutines.CoroutineExceptionHandler

internal abstract class BaseViewModel : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onCoroutineError(throwable)
    }
    protected val coroutineExecutor = CoroutineExecutor(viewModelScope, coroutineExceptionHandler)

    protected open fun onCoroutineError(throwable: Throwable) {
        // do nothing, let each subclass override this method accordingly
    }
}
