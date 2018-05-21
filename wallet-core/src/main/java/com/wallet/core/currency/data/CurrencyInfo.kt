package com.wallet.core.currency.data

import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
data class CurrencyInfo(val currency: Currency,
                        val price: BigDecimal?,
                        val date: Long?)