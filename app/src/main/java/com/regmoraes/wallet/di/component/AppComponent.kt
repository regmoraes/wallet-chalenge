package com.regmoraes.wallet.di.component

import com.regmoraes.wallet.di.module.AndroidModule
import com.regmoraes.wallet.di.module.DatabaseModule
import dagger.Component
import javax.inject.Singleton


/**
 * Copyright {2016} {Rômulo Eduardo G. Moraes}
 */
@Singleton
@Component(modules = [AndroidModule::class, DatabaseModule::class])
interface AppComponent {

    //fun pricesComponent(): PricesComponent
    fun marketComponent(): ViewComponent
}
