package com.wallet.core.wallet.data

import com.wallet.core.currency.data.Currency
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class Wallet(val currency: Currency,
             val amount: BigDecimal)