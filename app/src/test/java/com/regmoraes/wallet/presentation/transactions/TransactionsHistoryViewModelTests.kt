package com.regmoraes.wallet.presentation.transactions

import com.regmoraes.wallet.presentation.BaseTest
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.util.testObserver
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.domain.TransactionManager
import io.reactivex.Flowable
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.math.BigDecimal
import kotlin.test.assertTrue

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class TransactionsHistoryViewModelTests: BaseTest() {

    @Mock
    lateinit var transactionManager: TransactionManager

    private lateinit var viewModel: TransactionsHistoryViewModel

    override fun setUp() {
        super.setUp()
        viewModel = TransactionsHistoryViewModel(transactionManager)
    }

    @Test
    fun getTransactions_emitsCorrectResources() {

        val transactionOne =
                createFakeTransaction(TransactionType.SELL, BigDecimal(0), BigDecimal(10))

        val transactionTwo =
                createFakeTransaction(TransactionType.SELL, BigDecimal(0), BigDecimal(10))

        val transactions = listOf(transactionOne, transactionTwo)

        val expectedResources =
                listOf(Resource.loading(), Resource.success(transactions))

        val viewModelObserver=
                viewModel.getTransactionsResource().testObserver()

        `when`(transactionManager.getTransactions()).then {
            Flowable.just(transactions)
        }

        viewModel.getTransactions()

        verify(transactionManager, times(1)).getTransactions()

        assertTrue(expectedResources == viewModelObserver.observedValues)
    }

    private fun createFakeTransaction(transactionType: TransactionType,
                                      toDebitAmount: BigDecimal,
                                      toCreditAmount: BigDecimal): Transaction {

        val currencyToDebitInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)
        val currencyToCreditInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(10), 0L)

        return Transaction(currencyToDebitInfo.currency, toDebitAmount,
                currencyToCreditInfo.currency, toCreditAmount,
                transactionType, 0L)
    }
}
