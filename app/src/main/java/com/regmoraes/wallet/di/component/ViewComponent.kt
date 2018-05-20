package com.regmoraes.wallet.di.component

import com.regmoraes.wallet.di.ViewScope
import com.regmoraes.wallet.di.module.WalletModule
import com.regmoraes.wallet.presentation.market.MarketFragment
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryFragment
import dagger.Subcomponent


/**
 * Copyright {2016} {Rômulo Eduardo G. Moraes}
 */
@ViewScope
@Subcomponent(modules = [WalletModule::class])
interface ViewComponent {

    fun inject(marketFragment: MarketFragment)
    fun inject(transactionsHistoryFragment: TransactionsHistoryFragment)
}
