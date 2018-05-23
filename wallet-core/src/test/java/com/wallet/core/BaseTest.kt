package com.wallet.core

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
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

    @Before
    open fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    companion object {

        @JvmStatic
        @BeforeClass
        @Throws(Exception::class)
        fun beforeClass() {

            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }

        @JvmStatic
        @AfterClass
        @Throws(Exception::class)
        fun afterClass() {

            RxJavaPlugins.reset()
        }
    }
}