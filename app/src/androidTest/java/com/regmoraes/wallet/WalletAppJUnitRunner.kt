package com.regmoraes.wallet

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletAppJUnitRunner: AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, WalletTestApp::class.java.name, context)
    }

    override fun onStart() {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("Computation Scheduler"))
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("Io Scheduler"))
        RxJavaPlugins.setInitSingleSchedulerHandler(Rx2Idler.create("Single Scheduler"))

        super.onStart()
    }
}