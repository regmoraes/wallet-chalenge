package com.regmoraes.wallet.presentation.transactions

import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
data class PendingTransaction(var currencyInfo: CurrencyInfo? = null,
                              var amount: String? = null,
                              var transactionType: TransactionType? = null)