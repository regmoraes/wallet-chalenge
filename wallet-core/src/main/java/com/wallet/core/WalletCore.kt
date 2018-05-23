package com.wallet.core

import com.wallet.core.currency.domain.CurrencyManager
import com.wallet.core.currency.CurrencyModule
import com.wallet.core.market.domain.MarketManager
import com.wallet.core.market.MarketModule
import com.wallet.core.receipt.domain.ReceiptManager
import com.wallet.core.receipt.ReceiptModule
import com.wallet.core.wallet.domain.WalletManager
import com.wallet.core.wallet.WalletModule
import dagger.Component
import javax.inject.Singleton

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Component(modules = [WalletModule::class, CurrencyModule::class,
    MarketModule::class, ReceiptModule::class])
interface WalletCore {

    fun walletManager(): WalletManager

    fun currencyManager(): CurrencyManager

    fun marketManager(): MarketManager

    fun receiptManager(): ReceiptManager
}