package com.wallet.core.market.domain

import java.math.BigDecimal
import java.math.MathContext

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class ExchangeCalculator {

    fun exchange(fromCurrencyPrice: BigDecimal,
                 toCurrencyPrice: BigDecimal,
                 amount: BigDecimal) : BigDecimal {

        return (amount.multiply(calculateExchangeRate(fromCurrencyPrice, toCurrencyPrice)))
    }

    private fun calculateExchangeRate(fromCurrencyPrice: BigDecimal,
                        toCurrencyPrice: BigDecimal) : BigDecimal {

        return fromCurrencyPrice.divide(toCurrencyPrice, MathContext.DECIMAL128)
    }
}