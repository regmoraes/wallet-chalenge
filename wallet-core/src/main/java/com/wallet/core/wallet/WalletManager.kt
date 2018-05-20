package com.wallet.core.wallet

import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.WalletRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletManager (private val walletRepository: WalletRepository) {

    fun getCurrencyBalance(currency: Currency): Single<BigDecimal> {

        return walletRepository.getCurrencyBalance(currency)
    }

    fun getTotalBalance(): Single<BigDecimal> {

        return walletRepository.getTotalBalance()
    }

    fun credit(currency: Currency, value: BigDecimal): Completable {

        return walletRepository.credit(currency, value)
    }

    fun debit(currency: Currency, value: BigDecimal): Completable {

        return getCurrencyBalance(currency).flatMapCompletable { currentMoney ->

            if(currentMoney < value) {
                Completable.error(
                    IllegalStateException("Not enough money to debit $value. You have $currentMoney"))
            } else {
                walletRepository.debit(currency, value)
            }
        }
    }
}