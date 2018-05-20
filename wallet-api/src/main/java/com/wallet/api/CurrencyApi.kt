package com.wallet.api

import io.reactivex.Single

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface CurrencyApi {

    fun getInfoByInstant(instant: Long): Single<CurrencyInfo>
}