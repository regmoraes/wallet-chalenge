package com.regmoraes.wallet.presentation.util

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 *
 * Test Rule that take all jobs that subscribes to it will and queue and excuted one by one
 *
 * From https://medium.com/@nicolas.duponchel/testing-viewmodel-in-mvvm-using-livedata-and-rxjava-b27878495220
 */
class RxSchedulerRule : TestRule {

        override fun apply(base: Statement, description: Description) =
                object : Statement() {
                    override fun evaluate() {
                        RxAndroidPlugins.reset()
                        RxAndroidPlugins.setInitMainThreadSchedulerHandler { SCHEDULER_INSTANCE }

                        RxJavaPlugins.reset()
                        RxJavaPlugins.setIoSchedulerHandler { SCHEDULER_INSTANCE }
                        RxJavaPlugins.setNewThreadSchedulerHandler { SCHEDULER_INSTANCE }
                        RxJavaPlugins.setComputationSchedulerHandler { SCHEDULER_INSTANCE }

                        base.evaluate()
                    }
                }

        companion object {
            private val SCHEDULER_INSTANCE = Schedulers.trampoline()
        }
    }