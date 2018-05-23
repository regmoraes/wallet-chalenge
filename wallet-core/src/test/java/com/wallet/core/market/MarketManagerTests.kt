package com.wallet.core.market

import com.wallet.core.BaseTest
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.OperationType
import com.wallet.core.market.domain.ExchangeCalculator
import com.wallet.core.market.domain.MarketManager
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.domain.TransactionManager
import com.wallet.core.wallet.domain.WalletManager
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.math.BigDecimal

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
class MarketManagerTests : BaseTest() {

    @Mock lateinit var walletManagerMock: WalletManager
    @Mock lateinit var marketManager: MarketManager
    @Mock lateinit var transactionManager: TransactionManager
    private lateinit var exchangeCalculator: ExchangeCalculator

    override fun setUp() {
        super.setUp()

        exchangeCalculator = ExchangeCalculator()
        marketManager = MarketManager(walletManagerMock, transactionManager, exchangeCalculator)
    }

    @Test
    fun callTo_exchange_shouldCall_correct_transaction() {

        val currencyToDebitInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)
        val currencyToDebitAmount = BigDecimal(1)

        val currencyToCreditInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(10), 0L)
        val amountToExchange = BigDecimal(1)
        val currencyToCreditAmount =
            exchangeCalculator.exchange(currencyToDebitInfo.price!!, currencyToCreditInfo.price!!, amountToExchange)

        val expectedTransactions = Transaction(currencyToDebitInfo.currency, currencyToDebitAmount,
                currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.EXCHANGE, 0L)

        prepareTransactionFunMocks(currencyToDebitInfo.currency, currencyToDebitAmount,
            currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.EXCHANGE, expectedTransactions)

        marketManager.exchange(currencyToDebitInfo, currencyToCreditInfo, amountToExchange)
            .test()
            .await()
            .assertNoErrors()
            .assertValue(expectedTransactions)
            .assertComplete()

        verifyTransactionExecutedCorrectly(currencyToDebitInfo.currency, currencyToDebitAmount,
            currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.EXCHANGE)
    }

    @Test
    fun callTo_buy_shouldCall_correct_transaction() {

        val currencyToCreditAmount = BigDecimal(2)
        val currencyToCreditInfo = mock(CurrencyInfo::class.java)

        `when`(currencyToCreditInfo.price).then { BigDecimal(20) }
        `when`(currencyToCreditInfo.currency).then { Currency.BITCOIN }

        val currencyToDebit = Currency.BRL
        val currencyToDebitAmount = currencyToCreditInfo.price!! * currencyToCreditAmount

        val expectedTransactions = Transaction(currencyToDebit, currencyToDebitAmount,
                currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.BUY, 0L)

        prepareTransactionFunMocks(currencyToDebit, currencyToDebitAmount,
            currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.BUY, expectedTransactions)

        marketManager.buy(currencyToCreditInfo, currencyToCreditAmount)
                .test()
                .await()
                .assertNoErrors()
                .assertValue(expectedTransactions)
                .assertComplete()

        verifyTransactionExecutedCorrectly(currencyToDebit, currencyToDebitAmount,
            currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.BUY)
    }

    @Test
    fun callTo_sell_shouldCall_correct_transaction() {

        val currencyToDebitAmount = BigDecimal(10)
        val currencyToDebitInfo = mock(CurrencyInfo::class.java)

        `when`(currencyToDebitInfo.price).then { BigDecimal(20) }
        `when`(currencyToDebitInfo.currency).then { Currency.BITCOIN }

        val currencyToCredit = Currency.BRL
        val currencyToCreditAmount = currencyToDebitInfo.price!! * currencyToDebitAmount

        val expectedTransactions = Transaction(currencyToDebitInfo.currency, currencyToDebitAmount,
                currencyToCredit, currencyToCreditAmount, OperationType.SELL, 0L)

        prepareTransactionFunMocks(currencyToDebitInfo.currency, currencyToDebitAmount,
            currencyToCredit, currencyToCreditAmount, OperationType.SELL, expectedTransactions)

        marketManager.sell(currencyToDebitInfo, currencyToDebitAmount)
            .test()
            .await()
            .assertNoErrors()
            .assertValue(expectedTransactions)
            .assertComplete()

        verifyTransactionExecutedCorrectly(currencyToDebitInfo.currency, currencyToDebitAmount,
            currencyToCredit, currencyToCreditAmount, OperationType.SELL)
    }

    private fun prepareTransactionFunMocks(currencyToDebit: Currency, amountToDebit: BigDecimal,
                                           currencyToCredit: Currency, amountToCredit: BigDecimal,
                                           operationType: OperationType, transaction: Transaction) {

        `when`(walletManagerMock.debit(currencyToDebit, amountToDebit)).then { Completable.complete() }
        `when`(walletManagerMock.credit(currencyToCredit, amountToCredit)).then { Completable.complete() }

        `when`(transactionManager.createTransactions(currencyToDebit, amountToDebit,
            currencyToCredit, amountToCredit, operationType)).then { Single.just(transaction) }

    }

    private fun verifyTransactionExecutedCorrectly(currencyToDebit: Currency, amountToDebit: BigDecimal,
                                                   currencyToCredit: Currency, amountToCredit: BigDecimal,
                                                   operationType: OperationType) {

        verify(transactionManager, times(1))
            .createTransactions(currencyToDebit, amountToDebit,
                currencyToCredit, amountToCredit, operationType)
        verify(walletManagerMock, times(1)).debit(currencyToDebit, amountToDebit)
        verify(walletManagerMock, times(1)).credit(currencyToCredit, amountToCredit)
    }
}
