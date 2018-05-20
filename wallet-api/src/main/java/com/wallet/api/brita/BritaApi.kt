package com.wallet.api.brita

import com.wallet.api.ApiConfiguration
import com.wallet.api.CurrencyApi
import com.wallet.api.CurrencyInfo
import com.wallet.api.brita.data.toCurrencyInfo
import com.wallet.api.toDateTimeString
import io.reactivex.Single
import okhttp3.HttpUrl
import retrofit2.HttpException
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class BritaApi: CurrencyApi {

    private val retrofit = ApiConfiguration.buildRetrofit(baseUrl)
    private val baseRestService = retrofit.create(BritaRestService::class.java)

    override fun getInfoByInstant(instant: Long): Single<CurrencyInfo> {

        val dateTimeString = instant.toDateTimeString(DATE_TIME_QUERY_PATTERN)

        val url = HttpUrl.parse(baseUrl)!!
                .newBuilder()
                .addPathSegment("CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)")
                .addQueryParameter("@moeda", "'USD'")
                .addQueryParameter("@dataCotacao","'$dateTimeString'")
                .addQueryParameter("\$format", "json")
                .build()
                .toString()

        return baseRestService.getInfo(url).map { response ->

            if(response.isSuccessful) {

                response.body()!!.toCurrencyInfo()

            } else
                throw HttpException(response)
        }
    }

    companion object {
        const val DATE_TIME_QUERY_PATTERN = "MM-dd-yyyy"
        const val DATE_TIME_RESPONSE_PATTERN ="yyyy-MM-dd HH:mm:ss.SSS"

        var baseUrl = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/"
    }
}