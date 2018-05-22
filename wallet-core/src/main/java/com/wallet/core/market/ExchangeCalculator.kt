package com.wallet.core.market

import java.math.BigDecimal
import java.math.MathContext

/**
 * Copyright PEBMED Apps 2018
 */
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