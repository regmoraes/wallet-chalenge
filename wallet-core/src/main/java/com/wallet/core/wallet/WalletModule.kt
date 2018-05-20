package com.wallet.core.wallet

import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.WalletRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
@Module
class WalletModule(private val baseCurrency: Currency,
                   private val repository: WalletRepository) {

    @Provides
    @Singleton
    fun providesBaseCurrency(): Currency {
        return baseCurrency
    }

    @Provides
    @Singleton
    fun providesWalletRepository(): WalletRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providesWalletManager(baseCurrency: Currency, repository: WalletRepository): WalletManager {

        return WalletManager(baseCurrency, repository)
    }
}