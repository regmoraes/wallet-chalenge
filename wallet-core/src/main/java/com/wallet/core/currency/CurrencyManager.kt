package com.wallet.core.currency

import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.currency.data.CurrencyRepository
import io.reactivex.Single

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class CurrencyManager(private val currencyRepository: CurrencyRepository.Remote) {

    fun getCurrencyInfoByInstant(currency: Currency, instant: Long): Single<CurrencyInfo> {

        return currencyRepository.getInfoByDate(currency, instant)
    }
}