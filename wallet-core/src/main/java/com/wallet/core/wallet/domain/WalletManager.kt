package com.wallet.core.wallet.domain

import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.Wallet
import com.wallet.core.wallet.data.WalletRepository
import com.wallet.core.wallet.exception.InsufficientFundsException
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletManager (private val baseCurrency: Currency,
                     private val walletRepository: WalletRepository) {

    fun getWallets(): Single<List<Wallet>> {

        return walletRepository.getWallets()
    }

    fun getWallet(currency: Currency): Single<Wallet> {

        return walletRepository.getWallet(currency)
    }

    fun getBaseWallet(): Single<Wallet> {

        return walletRepository.getWallet(baseCurrency)
    }

    fun credit(currency: Currency, value: BigDecimal): Completable {

        return walletRepository.credit(currency, value)
    }

    fun debit(currency: Currency, value: BigDecimal): Completable {

        return getWallet(currency).flatMapCompletable { wallet ->

            if(wallet.amount < value) {
                Completable.error(
                        InsufficientFundsException("Not enough money to debit $value. You have ${wallet.amount}"))
            } else {
                walletRepository.debit(currency, value)
            }
        }
    }
}