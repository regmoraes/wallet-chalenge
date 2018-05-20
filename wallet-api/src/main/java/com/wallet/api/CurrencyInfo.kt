package com.wallet.api

import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
data class CurrencyInfo(val price: BigDecimal,
                        val date: Long)