package com.wallet.core

import com.wallet.core.currency.CurrencyModule
import com.wallet.core.market.MarketModule
import com.wallet.core.receipt.ReceiptModule
import com.wallet.core.receipt.ReceiptRepository
import com.wallet.core.wallet.WalletModule
import com.wallet.core.wallet.data.WalletRepository

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
object WalletCoreBuilder {

    fun build(walletRepository: WalletRepository, receiptRepository: ReceiptRepository): WalletCore {

        return DaggerWalletCore.builder()
                .walletModule(WalletModule(walletRepository))
                .receiptModule(ReceiptModule(receiptRepository))
                .currencyModule(CurrencyModule())
                .marketModule(MarketModule())
                .build()
    }
}