package com.wallet.core.receipt.data

import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface ReceiptRepository {

    fun getReceipts(): Flowable<List<Receipt>>

    fun saveReceipt(receipt: Receipt): Completable
}