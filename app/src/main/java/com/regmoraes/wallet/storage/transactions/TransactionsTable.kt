package com.regmoraes.wallet.storage.transactions

import com.regmoraes.wallet.storage.transactions.TransactionMapper.fromReceipt
import com.regmoraes.wallet.storage.transactions.TransactionMapper.toReceipt
import com.wallet.core.receipt.Receipt
import com.wallet.core.receipt.ReceiptRepository
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class TransactionsTable(private val transactionsDao: TransactionsDao) : ReceiptRepository {

    override fun getReceipts(): Flowable<List<Receipt>> {

        return transactionsDao.getTransactions()
            .map { transactions -> transactions.map { it -> toReceipt(it) } }
    }

    override fun saveReceipt(receipt: Receipt) : Completable {

        return Completable.fromCallable {
            transactionsDao.insertTransaction(fromReceipt(receipt))
        }
    }
}