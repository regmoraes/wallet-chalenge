package com.wallet.core.currency.data

import com.wallet.api.CurrencyApi
import com.wallet.core.currency.toCurrency
import io.reactivex.Flowable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class CurrencyApiManager(private val apis: HashMap<Currency, CurrencyApi>):
        CurrencyRepository.Remote {

    override fun getInfoByDate(currency: Currency, instant: Long): Single<CurrencyInfo> {

        return  apis[currency]!!
                .getInfoByInstant(instant)
                .map { (code, price, date) ->
                    CurrencyInfo(code.toCurrency(), price, date)
                }

    }

    override fun getAllCurrenciesInfo(instant: Long): Flowable<CurrencyInfo> {

        val apis = apis.values.map { it.getInfoByInstant(instant) }

        return Single.concat(apis).map { (code, price, date) ->

            CurrencyInfo(code.toCurrency(), price, date)
        }
    }
}