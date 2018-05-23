package com.wallet.api.bitcoin

import com.wallet.api.CurrencyInfo
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.intellij.lang.annotations.Language
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@RunWith(JUnit4::class)
class BitcoinApiTests {

    private val bitcoinApi = BitcoinApi()

    @Test
    fun return_currencyInfo_correctly_whenSuccessfulRequest() {

       @Language("JSON")
        val responseBody = "{\"ticker\": \n{\"high\": \"30992.99000000\", \"low\": \"27700.00000000\", \"vol\": \"158.35568167\", \n  \"last\": \"27779.01000000\", \"buy\": \"27779.00000000\", \"sell\": \"27779.01000000\", \"date\": 1527097861}}"
        val response = MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(responseBody)

        val apiMock = MockWebServer()
        apiMock.enqueue(response)
        apiMock.start()

        BitcoinApi.baseUrl = apiMock.url("/").toString()

        //"last:" JSON parameter
        val price = BigDecimal("27779.01000000")

        val todayInstant = Calendar.getInstance().timeInMillis
        val expectedCurrencyInfo = CurrencyInfo(BitcoinApi.currencyCode, price, todayInstant)

        bitcoinApi.getInfoByInstant(todayInstant)
                .test()
                .await()
                .assertNoErrors()
                .assertValue { currencyInfo ->

                    expectedCurrencyInfo.code == currencyInfo.code &&
                            expectedCurrencyInfo.price == currencyInfo.price
                }
                .assertComplete()

        apiMock.shutdown()
    }

    @Test
    fun return_emptyCurrencyInfo_whenHttpErrorResponse() {

        @Language("JSON")
        val errorResponseBody = "{}"
        val errorResponse = MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(errorResponseBody)

        val apiMock = MockWebServer()
        apiMock.enqueue(errorResponse)
        apiMock.start()

        BitcoinApi.baseUrl = apiMock.url("/").toString()

        val todayInstant = Calendar.getInstance().timeInMillis
        val expectedCurrencyInfo = CurrencyInfo(BitcoinApi.currencyCode, null, null)

        bitcoinApi.getInfoByInstant(todayInstant)
                .test()
                .await()
                .assertNoErrors()
                .assertValue(expectedCurrencyInfo)
                .assertComplete()

        apiMock.shutdown()
    }
}