package com.regmoraes.wallet.presentation.market

import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.OperationType

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
data class PendingTransaction(var currencyInfo: CurrencyInfo? = null,
                              var amount: String? = null,
                              var operationType: OperationType? = null)