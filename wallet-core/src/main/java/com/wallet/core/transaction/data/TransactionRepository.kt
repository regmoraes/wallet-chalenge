package com.wallet.core.transaction.data

import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface TransactionRepository {

    fun getTransactions(): Flowable<List<Transaction>>

    fun saveTransactions(transaction: Transaction): Completable
}