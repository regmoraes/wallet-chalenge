package com.regmoraes.wallet.persistence

import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.persistence.wallet.WalletEntity
import com.regmoraes.wallet.persistence.wallet.WalletsDao
import com.wallet.core.currency.data.Currency
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal


/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTests : BaseAppDatabaseTests() {

    private lateinit var walletsDao: WalletsDao

    @Before
    override fun setUp() {
        super.setUp()
        walletsDao = appDatabase.walletsDao()
    }

    @Test
    fun wallets_startsWith_correctBalance() {

        val expectedBrlStartBalance = BigDecimal(100000)
        val expectedOtherCurrenciesStartBalance = BigDecimal(0)

        walletsDao.insertAll(WalletEntity.defaultData())

        walletsDao.getWalletByCurrency(Currency.BRL)
            .test()
            .assertValue { it.amount == expectedBrlStartBalance }

        walletsDao.getWalletByCurrency(Currency.BRITA)
            .test()
            .assertValue { it.amount == expectedOtherCurrenciesStartBalance }

        walletsDao.getWalletByCurrency(Currency.BITCOIN)
            .test()
            .assertValue { it.amount == expectedOtherCurrenciesStartBalance }
    }
}