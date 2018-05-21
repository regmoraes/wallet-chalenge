package com.regmoraes.wallet

import android.app.Application
import com.regmoraes.wallet.di.component.AppComponent
import com.regmoraes.wallet.di.component.DaggerAppComponent
import com.regmoraes.wallet.di.module.AndroidModule
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber


/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
class WalletApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .androidModule(AndroidModule(this))
            .build()

        initJodaTime()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initJodaTime() {
        JodaTimeAndroid.init(this)
    }
}
