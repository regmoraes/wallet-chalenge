package com.regmoraes.wallet

import android.app.Application
import com.regmoraes.wallet.di.ComponentProvider
import com.regmoraes.wallet.di.component.AppComponent
import com.regmoraes.wallet.di.component.DaggerAppComponent
import com.regmoraes.wallet.di.component.ViewComponent
import com.regmoraes.wallet.di.module.AndroidModule
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber


/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
open class WalletApp : Application(), ComponentProvider {

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


    override fun getViewComponent(): ViewComponent {
        return appComponent.marketComponent()
    }

}
