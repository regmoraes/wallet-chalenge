package com.regmoraes.wallet

import android.app.Application
import com.regmoraes.wallet.di.ComponentProvider
import com.regmoraes.wallet.di.component.ViewComponent
import net.danlew.android.joda.JodaTimeAndroid
import org.mockito.Mockito.mock

class WalletTestApp : Application(), ComponentProvider {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
    }

    override fun getViewComponent(): ViewComponent {
        return mock(ViewComponent::class.java)
    }
}