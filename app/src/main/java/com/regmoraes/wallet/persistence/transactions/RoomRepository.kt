package com.regmoraes.wallet.persistence.transactions

import com.regmoraes.wallet.persistence.transactions.EntityMapper.fromTransactions
import com.regmoraes.wallet.persistence.transactions.EntityMapper.toTransactions
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.data.TransactionRepository
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class RoomRepository(private val transactionsDao: TransactionsDao) : TransactionRepository {

    override fun getTransactions(): Flowable<List<Transaction>> {

        return transactionsDao.getTransactions()
            .map { transactions -> transactions.map { it -> toTransactions(it) } }
    }

    override fun saveTransactions(transaction: Transaction): Completable {

        return Completable.fromCallable {
            transactionsDao.insert(fromTransactions(transaction))
        }
    }
}