package com.wallet.core.receipt

import com.wallet.core.receipt.data.ReceiptRepository
import com.wallet.core.receipt.domain.ReceiptManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
class ReceiptModule(private val receiptRepository: ReceiptRepository) {

    @Provides
    @Singleton
    fun providesReceiptRespository(): ReceiptRepository {
        return receiptRepository
    }

    @Provides
    @Singleton
    fun providesReceiptModule(receiptRepository: ReceiptRepository): ReceiptManager {

        return ReceiptManager(receiptRepository)
    }
}