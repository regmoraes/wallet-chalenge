package com.regmoraes.wallet.persistence.wallet

import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.Wallet
import com.wallet.core.wallet.data.WalletRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class RoomRepository(private val walletsDao: WalletsDao) : WalletRepository {

    override fun getWallet(currency: Currency): Single<Wallet> {

        return walletsDao.getWalletByCurrency(currency)
            .map { EntityMapper.toWallet(it) }
    }

    override fun getWallets(): Flowable<List<Wallet>> {

        return walletsDao.getWallets().map { walletEntities ->
            walletEntities.map { EntityMapper.toWallet(it) }
        }
    }

    override fun credit(currency: Currency, value: BigDecimal): Completable {

        return getWallet(currency).flatMapCompletable { wallet ->

            val updatedBalance = wallet.amount.plus(value)

            val walletEntity = WalletEntity(currency, updatedBalance)

            Completable.fromCallable { walletsDao.insert(walletEntity) }
        }
    }

    override fun debit(currency: Currency, value: BigDecimal): Completable {

        return getWallet(currency).flatMapCompletable { wallet ->

            val updatedBalance = wallet.amount.minus(value)

            val walletEntity = WalletEntity(currency, updatedBalance)

            Completable.fromCallable { walletsDao.insert(walletEntity) }
        }
    }
}