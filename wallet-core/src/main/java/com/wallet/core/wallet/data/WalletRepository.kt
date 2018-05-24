package com.wallet.core.wallet.data

import com.wallet.core.currency.data.Currency
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface WalletRepository {

    fun getWallet(currency: Currency): Single<Wallet>

    fun getWallets(): Flowable<List<Wallet>>

    fun credit(currency: Currency, value: BigDecimal): Completable

    fun debit(currency: Currency, value: BigDecimal): Completable
}