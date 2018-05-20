package com.wallet.core.wallet.data

import com.wallet.core.currency.data.Currency
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface WalletRepository {

    fun getCurrencyBalance(currency: Currency): Single<BigDecimal>

    fun getTotalBalance(): Single<BigDecimal>

    fun credit(currency: Currency, value: BigDecimal): Completable

    fun debit(currency: Currency, value: BigDecimal): Completable
}