package com.wallet.api.brita

import com.wallet.api.brita.data.InfoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
internal interface BritaRestService {

    @GET
    fun getInfo(@Url url: String): Single<Response<InfoResponse>>
}