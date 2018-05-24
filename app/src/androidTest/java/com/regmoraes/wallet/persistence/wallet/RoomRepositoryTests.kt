package com.regmoraes.wallet.persistence.wallet

import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.persistence.BaseAppDatabaseTests
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
class RoomRepositoryTests : BaseAppDatabaseTests() {

    private lateinit var walletsDao: WalletsDao

    @Before
    override fun setUp() {
        super.setUp()
        walletsDao = appDatabase.walletsDao()
    }

    @Test
    fun getWalletByCurrency_returnsWallet_correctly() {

        val wallet = WalletEntity(Currency.BRL, BigDecimal(0))
        val expectedWallet = WalletEntity(Currency.BRITA, BigDecimal(0))

        walletsDao.insertAll(arrayOf(wallet, expectedWallet))

        walletsDao.getWalletByCurrency(Currency.BRITA)
            .test()
            .assertValue(expectedWallet)
    }
}