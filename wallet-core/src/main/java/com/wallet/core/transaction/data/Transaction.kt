package com.wallet.core.transaction.data

import com.wallet.core.currency.data.Currency
import com.wallet.core.market.data.OperationType
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
data class Transaction(val debitCurrency: Currency,
                       val debitCurrencyAmount: BigDecimal,
                       val creditCurrency: Currency,
                       val creditCurrencyAmount: BigDecimal,
                       val operationType: OperationType,
                       val date: Long)