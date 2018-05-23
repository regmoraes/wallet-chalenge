package com.wallet.core.receipt

import com.wallet.core.receipt.data.ReceiptRepository
import com.wallet.core.receipt.domain.ReceiptManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Copyright PEBMED Apps 2018
 */
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