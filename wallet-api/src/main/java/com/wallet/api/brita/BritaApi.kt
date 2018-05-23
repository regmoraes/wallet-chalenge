package com.wallet.api.brita

import com.wallet.api.ApiConfiguration
import com.wallet.api.CurrencyApi
import com.wallet.api.CurrencyInfo
import com.wallet.api.brita.data.toCurrencyInfo
import com.wallet.api.toDateTimeString
import io.reactivex.Single
import okhttp3.HttpUrl
import org.joda.time.LocalDateTime

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class BritaApi: CurrencyApi {

    private val retrofit = ApiConfiguration.buildRetrofit(baseUrl)
    private val baseRestService = retrofit.create(BritaRestService::class.java)

    override fun getInfoByInstant(instant: Long): Single<CurrencyInfo> {

        val requestUrl = createRequestUrlForInstant(instant)

        return createGetCurrencyInfoSingle(requestUrl)
                .onErrorResumeNext { error ->

                    when(error) {

                        is IllegalStateException -> {

                            val fallbackUrl = createFallbackRequestUrl()
                            createGetCurrencyInfoSingle(fallbackUrl)
                        }

                        else ->Single.just(CurrencyInfo(currencyCode, null, null))
                    }
                }
    }

    private fun createGetCurrencyInfoSingle(requestUrl: String): Single<CurrencyInfo> {

        return baseRestService.getInfo(requestUrl)
                .map { response ->

                    if(response.isSuccessful) {
                        response.body()!!.toCurrencyInfo()

                    } else
                        CurrencyInfo(currencyCode, null, null)
                }
    }

    private fun createRequestUrlForInstant(instant: Long): String {

        val dateTimeString = instant.toDateTimeString(DATE_TIME_QUERY_PATTERN)

        return HttpUrl.parse(baseUrl)!!
                .newBuilder()
                .addPathSegment("CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)")
                .addQueryParameter("@moeda", "'USD'")
                .addQueryParameter("@dataCotacao","'$dateTimeString'")
                .addQueryParameter("\$format", "json")
                .build()
                .toString()
    }

    /**
     * Fallback method when no values are returned by BRITA currency API.
     *
     * Sometimes the API used for BRITA currency info fetching does not respond with updated values
     * for the provided day (as instant). By calling this method, a new request will be made
     * to the API, but it will ask for info from the PREVIOUS DAY.
     *
     * @return The request URL
     */
    private fun createFallbackRequestUrl(): String {

        val yesterdayInstant = LocalDateTime.now().minusDays(1).toDateTime().millis

        return createRequestUrlForInstant(yesterdayInstant)
    }

    companion object {
        const val currencyCode = "BRITA"
        const val DATE_TIME_QUERY_PATTERN = "MM-dd-yyyy"
        const val DATE_TIME_RESPONSE_PATTERN ="yyyy-MM-dd HH:mm:ss.SSS"

        var baseUrl = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/"
    }
}