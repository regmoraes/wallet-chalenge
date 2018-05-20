package com.wallet.core.currency.data

import com.wallet.api.CurrencyApi
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class CurrencyApiManager(private val apis: HashMap<Currency, CurrencyApi>):
    CurrencyRepository.Remote {

    override fun getInfoByDate(currency: Currency, instant: Long): Single<CurrencyInfo> {

       return when(currency) {

            Currency.BITCOIN -> {

                apis[Currency.BITCOIN]!!
                    .getInfoByInstant(instant)
                    .map { (price, date) -> CurrencyInfo(Currency.BITCOIN, price, date) }
            }

           Currency.BRITA -> {

               apis[Currency.BRITA]!!
                       .getInfoByInstant(instant)
                       .map { (price, date) -> CurrencyInfo(Currency.BRITA, price, date) }
           }

           Currency.BRL -> {

               val currencyInfo = CurrencyInfo(Currency.BRL, BigDecimal(1), Calendar.getInstance().timeInMillis)

               Single.just(currencyInfo)
           }
        }
    }
}