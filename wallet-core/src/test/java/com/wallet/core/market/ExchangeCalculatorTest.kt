package com.wallet.core.market

import com.wallet.core.BaseTest
import com.wallet.core.market.domain.ExchangeCalculator
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
class ExchangeCalculatorTest : BaseTest() {

    private val exchangeCalculator = ExchangeCalculator()

    @Test
    fun exchange_correctly_when_fromCurrencyPrice_smaller_than_toCurrencyPrice() {

        val fromCurrencyPrice = BigDecimal(2)
        val toCurrencyPrice = BigDecimal(10)
        val amountToExchange = BigDecimal(2)

        val expectedExchangeAmount = BigDecimal("0.4")

        val actualExchangeAmount = exchangeCalculator.exchange(
            fromCurrencyPrice, toCurrencyPrice, amountToExchange)

        assertEquals(expectedExchangeAmount, actualExchangeAmount)
    }

    @Test
    fun exchange_correctly_when_fromCurrencyPrice_greater_than_toCurrencyPrice() {

        val fromCurrencyPrice = BigDecimal(30)
        val toCurrencyPrice = BigDecimal(20)

        val amountToExchange = BigDecimal(2)

        val expectedExchangeAmount = BigDecimal("3.0")

        val actualExchangeAmount = exchangeCalculator.exchange(
            fromCurrencyPrice, toCurrencyPrice, amountToExchange)

        assertEquals(expectedExchangeAmount, actualExchangeAmount)
    }
}