package com.wallet.core.transaction

import com.wallet.core.market.data.TransactionType

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
fun String.toTransactionType(): TransactionType {

    return when (this) {

        TransactionType.EXCHANGE.name -> TransactionType.EXCHANGE
        TransactionType.BUY.name -> TransactionType.BUY
        TransactionType.SELL.name -> TransactionType.SELL
        else -> throw IllegalArgumentException()
    }
}
