package com.wallet.api.brita

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
class BritaApiTests {

    private val britaApi = BritaApi()

    @Test
    fun return_currencyInfo_correctly_whenSuccessfulRequest() {

        @Language("JSON")
        val responseBody = "{\"@odata.context\":\"https://was-p/olinda/servico/PTAX/versao/v1/odata${'$'}metadata#_CotacaoMoedaDia\",\"value\":[{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6552,\"cotacaoVenda\":3.6559,\"dataHoraCotacao\":\"2018-05-22 10:11:18.698\",\"tipoBoletim\":\"Abertura\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6560,\"cotacaoVenda\":3.6566,\"dataHoraCotacao\":\"2018-05-22 11:05:19.341\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6465,\"cotacaoVenda\":3.6471,\"dataHoraCotacao\":\"2018-05-22 12:07:18.814\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6406,\"cotacaoVenda\":3.6412,\"dataHoraCotacao\":\"2018-05-22 13:06:52.926\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6496,\"cotacaoVenda\":3.6502,\"dataHoraCotacao\":\"2018-05-22 13:06:52.938\",\"tipoBoletim\":\"Fechamento PTAX\"}]}"
        val response = MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(responseBody)

        val apiMock = MockWebServer()
        apiMock.enqueue(response)
        apiMock.start()

        BritaApi.baseUrl = apiMock.url("/").toString()

        val todayInstant = Calendar.getInstance().timeInMillis
        val expectedCurrencyInfo = CurrencyInfo(BritaApi.currencyCode, BigDecimal("3.6496"), todayInstant)

        britaApi.getInfoByInstant(todayInstant)
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
    fun return_currencyInfo_forTipoBoletimIntermediario_whenResponse_doesNotHave_tipoBoletimPtax() {

       @Language("JSON")
        val responseBody = "{\"@odata.context\":\"https://was-p/olinda/servico/PTAX/versao/v1/odata${'$'}metadata#_CotacaoMoedaDia\",\"value\":[{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6552,\"cotacaoVenda\":3.6559,\"dataHoraCotacao\":\"2018-05-22 10:11:18.698\",\"tipoBoletim\":\"Abertura\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6560,\"cotacaoVenda\":3.6566,\"dataHoraCotacao\":\"2018-05-22 11:05:19.341\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6465,\"cotacaoVenda\":3.6471,\"dataHoraCotacao\":\"2018-05-22 12:07:18.814\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6406,\"cotacaoVenda\":3.6412,\"dataHoraCotacao\":\"2018-05-22 13:06:52.926\",\"tipoBoletim\":\"Intermediário\"}]}"
        val response = MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(responseBody)

        val apiMock = MockWebServer()
        apiMock.enqueue(response)
        apiMock.start()

        BritaApi.baseUrl = apiMock.url("/").toString()

        // "cotacaoCompra:" JSON parameter
        val expectedPrice = BigDecimal("3.6406")

        val todayInstant = Calendar.getInstance().timeInMillis
        val expectedCurrencyInfo = CurrencyInfo(BritaApi.currencyCode, expectedPrice, todayInstant)

        britaApi.getInfoByInstant(todayInstant)
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
    fun call_fallbackRequest_whenNoValuesReturned_forBaseRequest() {

        @Language("JSON")
        val baseRequestResponseBody = "{\"@odata.context\":\"https://was-p/olinda/servico/PTAX/versao/v1/odata${'$'}metadata#_CotacaoMoedaDia\",\"value\":[]}"
        val baseRequestResponse = MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(baseRequestResponseBody)

        @Language("JSON")
        val fallbackResponseBody = "{\"@odata.context\":\"https://was-p/olinda/servico/PTAX/versao/v1/odata${'$'}metadata#_CotacaoMoedaDia\",\"value\":[{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6552,\"cotacaoVenda\":3.6559,\"dataHoraCotacao\":\"2018-05-22 10:11:18.698\",\"tipoBoletim\":\"Abertura\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6560,\"cotacaoVenda\":3.6566,\"dataHoraCotacao\":\"2018-05-22 11:05:19.341\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6465,\"cotacaoVenda\":3.6471,\"dataHoraCotacao\":\"2018-05-22 12:07:18.814\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6406,\"cotacaoVenda\":3.6412,\"dataHoraCotacao\":\"2018-05-22 13:06:52.926\",\"tipoBoletim\":\"Intermediário\"},{\"paridadeCompra\":1.0000,\"paridadeVenda\":1.0000,\"cotacaoCompra\":3.6496,\"cotacaoVenda\":3.6502,\"dataHoraCotacao\":\"2018-05-22 13:06:52.938\",\"tipoBoletim\":\"Fechamento PTAX\"}]}"
        val fallbackResponse = MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(fallbackResponseBody)

        val apiMock = MockWebServer()
        apiMock.enqueue(baseRequestResponse)
        apiMock.enqueue(fallbackResponse)
        apiMock.start()

        BritaApi.baseUrl = apiMock.url("/").toString()

        val todayInstant = Calendar.getInstance().timeInMillis
        val expectedCurrencyInfo = CurrencyInfo(BritaApi.currencyCode, BigDecimal("3.6496"), todayInstant)

        britaApi.getInfoByInstant(todayInstant)
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

        BritaApi.baseUrl = apiMock.url("/").toString()

        val todayInstant = Calendar.getInstance().timeInMillis
        val expectedCurrencyInfo = CurrencyInfo(BritaApi.currencyCode, null, null)

        britaApi.getInfoByInstant(todayInstant)
                .test()
                .await()
                .assertNoErrors()
                .assertValue(expectedCurrencyInfo)
                .assertComplete()

        apiMock.shutdown()
    }
}