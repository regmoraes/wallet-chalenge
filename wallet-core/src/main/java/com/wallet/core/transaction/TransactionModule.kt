package com.wallet.core.transaction

import com.wallet.core.transaction.data.TransactionRepository
import com.wallet.core.transaction.domain.TransactionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
class TransactionModule(private val transactionRepository: TransactionRepository) {

    @Provides
    @Singleton
    fun providesTransactionsRespository(): TransactionRepository {
        return transactionRepository
    }

    @Provides
    @Singleton
    fun providesTransactionsModule(transactionRepository: TransactionRepository): TransactionManager {

        return TransactionManager(transactionRepository)
    }
}