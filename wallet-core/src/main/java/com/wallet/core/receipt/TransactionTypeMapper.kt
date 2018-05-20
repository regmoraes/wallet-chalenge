package com.wallet.core.receipt

import com.wallet.core.market.OperationType

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
fun String.toTransactionType(): OperationType {

    return when (this) {

        OperationType.EXCHANGE.name -> OperationType.EXCHANGE
        OperationType.BUY.name -> OperationType.BUY
        OperationType.SELL.name -> OperationType.SELL
        else -> throw IllegalArgumentException()
    }
}
