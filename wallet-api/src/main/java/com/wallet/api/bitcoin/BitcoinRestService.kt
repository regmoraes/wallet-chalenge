package com.wallet.api.bitcoin

import com.wallet.api.bitcoin.data.InfoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
internal interface BitcoinRestService {

    @GET
    fun getInfo(@Url url: String): Single<Response<InfoResponse>>
}