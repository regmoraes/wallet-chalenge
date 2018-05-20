package com.regmoraes.wallet.di.module

import android.content.Context
import com.regmoraes.wallet.storage.AppDatabase
import com.regmoraes.wallet.storage.transactions.TransactionsTable
import com.regmoraes.wallet.storage.wallet.WalletsTable
import com.wallet.core.receipt.ReceiptRepository
import com.wallet.core.wallet.data.WalletRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
@Module
class StorageModule {

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context) : AppDatabase {

        return AppDatabase.getInstance(context)!!
    }

    @Provides
    @Singleton
    fun providesWalletRepository(appDatabase: AppDatabase) : WalletRepository {

        return WalletsTable(appDatabase.walletsDao())
    }

    @Provides
    @Singleton
    fun providesReceiptRepository(appDatabase: AppDatabase) : ReceiptRepository {

        return TransactionsTable(appDatabase.transactionsDao())
    }
}