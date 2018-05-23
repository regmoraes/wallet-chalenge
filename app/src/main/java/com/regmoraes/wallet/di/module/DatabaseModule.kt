package com.regmoraes.wallet.di.module

import android.content.Context
import com.regmoraes.wallet.persistence.AppDatabase
import com.regmoraes.wallet.persistence.wallet.RoomRepository
import com.wallet.core.receipt.data.ReceiptRepository
import com.wallet.core.wallet.data.WalletRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
@Module
open class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context) : AppDatabase {

        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesWalletRepository(appDatabase: AppDatabase) : WalletRepository {

        return RoomRepository(appDatabase.walletsDao())
    }

    @Provides
    @Singleton
    open fun providesReceiptRepository(appDatabase: AppDatabase) : ReceiptRepository {

        return com.regmoraes.wallet.persistence.transactions.RoomRepository(appDatabase.transactionsDao())
    }
}