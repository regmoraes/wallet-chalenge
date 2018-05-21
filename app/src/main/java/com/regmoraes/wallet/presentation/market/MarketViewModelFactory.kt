package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wallet.core.currency.CurrencyManager
import com.wallet.core.market.MarketManager
import com.wallet.core.wallet.WalletManager
import timber.log.Timber
import javax.inject.Inject

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
class MarketViewModelFactory @Inject
    constructor(private val marketManager: MarketManager,
                private val currencyManager: CurrencyManager,
                private val walletManager: WalletManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {

            Timber.d("ViewModel Created")
            return MarketViewModel(marketManager, currencyManager, walletManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
