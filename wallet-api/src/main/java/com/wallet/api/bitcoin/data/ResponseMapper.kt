package com.wallet.api.bitcoin.data

import com.wallet.api.CurrencyInfo
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
fun InfoResponse.toCurrencyInfo() : CurrencyInfo {

    val price = BigDecimal(ticker.last)

    return CurrencyInfo("BITCOIN", price, ticker.date)
}