package com.regmoraes.wallet.storage.transactions

import com.wallet.core.receipt.Receipt
import com.wallet.core.receipt.ReceiptRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class TransactionsTest(private val receipts: List<Receipt>) : ReceiptRepository {

    override fun getReceipts(): Flowable<List<Receipt>> {

        return Flowable.just(receipts)
    }

    override fun saveReceipt(receipt: Receipt) : Completable {

        return Completable.complete()
    }
}