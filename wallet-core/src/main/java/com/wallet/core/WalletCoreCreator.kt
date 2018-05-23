package com.wallet.core

import com.wallet.core.currency.CurrencyModule
import com.wallet.core.currency.data.Currency
import com.wallet.core.market.MarketModule
import com.wallet.core.transaction.TransactionModule
import com.wallet.core.transaction.data.TransactionRepository
import com.wallet.core.wallet.WalletModule
import com.wallet.core.wallet.data.WalletRepository

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
object WalletCoreCreator {

    fun create(baseCurrency: Currency,
               walletRepository: WalletRepository,
               transactionRepository: TransactionRepository): WalletCore {

        return DaggerWalletCore.builder()
                .walletModule(WalletModule(baseCurrency, walletRepository))
                .transactionModule(TransactionModule(transactionRepository))
                .currencyModule(CurrencyModule())
                .marketModule(MarketModule())
                .build()
    }
}