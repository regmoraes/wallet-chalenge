package com.wallet.core.wallet

import com.wallet.core.wallet.data.WalletRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
@Module
class WalletModule(private val repository: WalletRepository) {

    @Provides
    @Singleton
    fun providesWalletRepository(): WalletRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providesWalletManager(repository: WalletRepository): WalletManager {

        return WalletManager(repository)
    }
}