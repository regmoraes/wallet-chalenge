package com.regmoraes.wallet.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.regmoraes.wallet.presentation.util.RxSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {

    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @JvmField @Rule
    val testRule = RxSchedulerRule()

    @Before
    open fun setUp(){
        MockitoAnnotations.initMocks(this)
    }
}