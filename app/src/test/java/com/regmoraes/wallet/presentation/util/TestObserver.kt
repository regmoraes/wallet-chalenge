package com.regmoraes.wallet.presentation.util

import android.arch.lifecycle.Observer

/**
 * ViewModel Observer test utility class that stores emitted values from a ViewModel
 * and allow the verification of these values.
 *
 * From https://medium.com/@nicolas.duponchel/testing-viewmodel-in-mvvm-using-livedata-and-rxjava-b27878495220
 */
class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}