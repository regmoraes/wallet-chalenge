package com.regmoraes.wallet.di

import com.regmoraes.wallet.di.component.ViewComponent

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface ComponentProvider {

    fun getViewComponent(): ViewComponent
}