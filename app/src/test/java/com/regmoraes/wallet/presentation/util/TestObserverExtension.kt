package com.regmoraes.wallet.presentation.util

import android.arch.lifecycle.LiveData

/**
 * ViewModel Observer extension fuction to easily create a TestObserver
 *
 * From https://medium.com/@nicolas.duponchel/testing-viewmodel-in-mvvm-using-livedata-and-rxjava-b27878495220
 */
fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}