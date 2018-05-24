package com.regmoraes.wallet.persistence.transactions

import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.persistence.BaseAppDatabaseTests
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
@RunWith(AndroidJUnit4::class)
class RoomRepositoryTests : BaseAppDatabaseTests() {

    private lateinit var transactionsDao: TransactionsDao

    @Before
    override fun setUp() {
        super.setUp()
        transactionsDao = appDatabase.transactionsDao()
    }

    @Test
    fun getTransactions_OrderedBy_LatestFirst() {

        val transactionOne =
                createFakeTransaction(
                        1, TransactionType.SELL, BigDecimal(0), BigDecimal(10)
                )
        val transactionTwo =
                createFakeTransaction(
                        2, TransactionType.SELL, BigDecimal(0), BigDecimal(10)
                )

        transactionsDao.insertAll(arrayOf(transactionOne, transactionTwo))

        transactionsDao.getTransactions()
                .test()
                .assertValue(listOf(transactionTwo, transactionOne))
    }

    private fun createFakeTransaction(id: Long, transactionType: TransactionType,
                                      toDebitAmount: BigDecimal,
                                      toCreditAmount: BigDecimal): TransactionEntity {

        val currencyToDebitInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)
        val currencyToCreditInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(10), 0L)

        return TransactionEntity(id, currencyToDebitInfo.currency, toDebitAmount,
                currencyToCreditInfo.currency, toCreditAmount,
                transactionType, 0L)
    }
}