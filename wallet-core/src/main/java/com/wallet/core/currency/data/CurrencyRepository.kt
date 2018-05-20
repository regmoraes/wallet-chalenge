package com.wallet.core.currency.data

import io.reactivex.Single

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface CurrencyRepository {

    interface Remote {

        fun getInfoByDate(currency: Currency, instant: Long): Single<CurrencyInfo>
    }
}