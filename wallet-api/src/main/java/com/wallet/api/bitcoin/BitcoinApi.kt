package com.wallet.api.bitcoin

import com.wallet.api.ApiConfiguration
import com.wallet.api.CurrencyApi
import com.wallet.api.CurrencyInfo
import com.wallet.api.bitcoin.data.toCurrencyInfo
import io.reactivex.Single
import okhttp3.HttpUrl

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class BitcoinApi: CurrencyApi {

    private val retrofit = ApiConfiguration.buildRetrofit(baseUrl)
    private val baseRestService = retrofit.create(BitcoinRestService::class.java)

    override fun getInfoByInstant(instant: Long): Single<CurrencyInfo> {

        //instant ignored

        val url = HttpUrl.parse(baseUrl)!!
                .newBuilder()
                .addPathSegment("BTC")
                .addPathSegment("ticker")
                .build()
                .toString()

        return baseRestService.getInfo(url)
                .map { response ->

                    if(response.isSuccessful) {
                        response.body()!!.toCurrencyInfo()

                    } else
                        CurrencyInfo(currencyCode, null, null)
                }
                .onErrorReturn { CurrencyInfo(currencyCode, null, null) }
    }

    companion object {
        const val currencyCode = "BITCOIN"

        var baseUrl = "https://www.mercadobitcoin.net/api/"
    }
}