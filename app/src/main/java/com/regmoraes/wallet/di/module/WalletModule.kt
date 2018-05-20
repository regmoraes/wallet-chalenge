package com.regmoraes.wallet.di.module

import com.regmoraes.wallet.di.ViewScope
import com.wallet.core.WalletCore
import com.wallet.core.WalletCoreBuilder
import com.wallet.core.currency.CurrencyManager
import com.wallet.core.currency.data.Currency
import com.wallet.core.market.MarketManager
import com.wallet.core.receipt.ReceiptManager
import com.wallet.core.receipt.ReceiptRepository
import com.wallet.core.wallet.WalletManager
import com.wallet.core.wallet.data.WalletRepository
import dagger.Module
import dagger.Provides

/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
@Module
class WalletModule {

    @Provides
    @ViewScope
    fun providesBaseCurrency() : Currency {

        return Currency.BRL
    }

    @Provides
    @ViewScope
    fun providesWalletCore(baseCurrency: Currency,
                           walletRepository: WalletRepository,
                           receiptRepository: ReceiptRepository) : WalletCore {

        return WalletCoreBuilder.build(baseCurrency, walletRepository, receiptRepository)
    }

    @Provides
    @ViewScope
    fun provideMarketModule(walletCore: WalletCore) : MarketManager {

        return walletCore.marketManager()
    }

    @Provides
    @ViewScope
    fun providesWalletManager(walletCore: WalletCore) : WalletManager {

        return walletCore.walletManager()
    }

    @Provides
    @ViewScope
    fun provideCurrencyManager(walletCore: WalletCore) : CurrencyManager {

        return walletCore.currencyManager()
    }

    @Provides
    @ViewScope
    fun providesReceiptManager(walletCore: WalletCore) : ReceiptManager {

        return walletCore.receiptManager()
    }
}