package com.regmoraes.wallet.di.component

import com.regmoraes.wallet.di.module.WalletCoreModule
import com.regmoraes.wallet.di.scope.ViewScope
import com.regmoraes.wallet.presentation.market.MarketFragment
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryFragment
import com.regmoraes.wallet.presentation.wallet.WalletFragment
import dagger.Subcomponent


/**
 * Copyright {2016} {Rômulo Eduardo G. Moraes}
 */
@ViewScope
@Subcomponent(modules = [WalletCoreModule::class])
interface ViewComponent {

    fun inject(marketFragment: MarketFragment)
    fun inject(transactionsHistoryFragment: TransactionsHistoryFragment)
    fun inject(walletFragment: WalletFragment)
}
