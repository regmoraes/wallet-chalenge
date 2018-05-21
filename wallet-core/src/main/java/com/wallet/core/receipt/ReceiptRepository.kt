package com.wallet.core.receipt

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface ReceiptRepository {

    fun getReceipts(): Flowable<List<Receipt>>

    fun saveReceipt(receipt: Receipt): Completable
}