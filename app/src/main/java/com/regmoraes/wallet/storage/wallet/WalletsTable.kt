package com.regmoraes.wallet.storage.wallet

import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.WalletRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletsTable(private val walletsDao: WalletsDao) : WalletRepository {

    override fun getCurrencyBalance(currency: Currency): Single<BigDecimal> {

        return walletsDao.getWalletByCurrency(currency.name).map { wallet -> BigDecimal(wallet.amount) }
    }

    override fun getTotalBalance(): Single<BigDecimal> {

        return getCurrencyBalance(Currency.BRL)
    }

    override fun credit(currency: Currency, value: BigDecimal): Completable {

        return getCurrencyBalance(currency).flatMapCompletable { currentBalance ->

            val updatedBalance = currentBalance.plus(value)

            val walletEntity = WalletEntity(currency.name, updatedBalance.toPlainString())

            Completable.fromCallable { walletsDao.insert(walletEntity) }
        }
    }

    override fun debit(currency: Currency, value: BigDecimal): Completable {

        return getCurrencyBalance(currency).flatMapCompletable { currentBalance ->

            val updatedBalance = currentBalance.minus(value)

            val walletEntity = WalletEntity(currency.name, updatedBalance.toPlainString())

            Completable.fromCallable { walletsDao.insert(walletEntity) }
        }
    }
}