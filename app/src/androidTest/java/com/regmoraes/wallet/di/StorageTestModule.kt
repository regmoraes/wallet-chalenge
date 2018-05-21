package com.regmoraes.wallet.di

import com.regmoraes.wallet.di.module.StorageModule
import com.regmoraes.wallet.storage.AppDatabase
import com.regmoraes.wallet.storage.transactions.TransactionsTest
import com.wallet.core.receipt.Receipt
import com.wallet.core.receipt.ReceiptRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2016} {Rômulo Eduardo G. Moraes}
 **/
@Module
class StorageTestModule(private val receipts: List<Receipt>) : StorageModule() {

    @Provides
    @Singleton
    override fun providesReceiptRepository(appDatabase: AppDatabase) : ReceiptRepository {

        return TransactionsTest(receipts)
    }
}