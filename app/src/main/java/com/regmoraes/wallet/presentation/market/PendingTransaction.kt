package com.regmoraes.wallet.presentation.market

import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.OperationType
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
data class PendingTransaction(var currencyInfo: CurrencyInfo? = null,
                              var amount: String? = null,
                              var operationType: OperationType? = null)