package com.regmoraes.wallet.di.module

import com.regmoraes.wallet.di.scope.ViewScope
import com.wallet.core.WalletCore
import com.wallet.core.WalletCoreCreator
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.domain.CurrencyManager
import com.wallet.core.market.domain.MarketManager
import com.wallet.core.transaction.data.TransactionRepository
import com.wallet.core.transaction.domain.TransactionManager
import com.wallet.core.wallet.data.WalletRepository
import com.wallet.core.wallet.domain.WalletManager
import dagger.Module
import dagger.Provides

/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
@Module
class WalletCoreModule {

    @Provides
    @ViewScope
    fun providesBaseCurrency() : Currency {

        return Currency.BRL
    }

    @Provides
    @ViewScope
    fun providesWalletCore(baseCurrency: Currency,
                           walletRepository: WalletRepository,
                           transactionRepository: TransactionRepository) : WalletCore {

        return WalletCoreCreator.create(baseCurrency, walletRepository, transactionRepository)
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
    fun providesTransactionsManager(walletCore: WalletCore) : TransactionManager {

        return walletCore.transactionManager()
    }
}