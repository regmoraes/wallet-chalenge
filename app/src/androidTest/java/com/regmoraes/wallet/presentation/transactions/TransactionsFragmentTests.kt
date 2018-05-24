package com.regmoraes.wallet.presentation.transactions

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.R
import com.regmoraes.wallet.SingleFragmentActivity
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType
import com.wallet.core.transaction.data.Transaction
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.math.BigDecimal


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TransactionsFragmentTests {

    private val transactionsResource = MutableLiveData<Resource<List<Transaction>>>()

    @Rule
    @JvmField
    val activityTesRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java)

    @Before
    fun setUp() {

        val fragment = TransactionsHistoryFragment.newInstance()
        val viewModelFactoryMock = mock(TransactionsHistoryViewModelFactory::class.java)
        val viewModelMock = Mockito.mock(TransactionsHistoryViewModel::class.java)

        `when`(viewModelFactoryMock.create(TransactionsHistoryViewModel::class.java)).then { viewModelMock }
        `when`(viewModelMock.getTransactionsResource()).then { transactionsResource }

        fragment.viewModelFactory = viewModelFactoryMock

        activityTesRule.activity.setFragment(fragment)
    }

    @Test
    fun show_Transaction_Transactions_Correctly() {

        val resources = activityTesRule.activity.resources
        val transaction = createFakeTransactions()

        transactionsResource.postValue(Resource.success(listOf(transaction)))

        onView(withId(R.id.textView_credited_amount))
            .check(matches(withText(
                String.format(resources.getString(R.string.transaction_history_amount_format),
                transaction.creditCurrencyAmount.toPlainString()))))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_currency_credited))
            .check(matches(withText(transaction.creditCurrency.name)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_debited_amount))
            .check(matches(withText(
                String.format(resources.getString(R.string.transaction_history_amount_format),
                    transaction.debitCurrencyAmount.toPlainString()))))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_currency_debited))
            .check(matches(withText(transaction.debitCurrency.name)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun show_Message_When_Empty_Transactions() {

        transactionsResource.postValue(Resource.success(emptyList()))

        val errorText = activityTesRule.activity.getString(R.string.transaction_empty)

        onView(withId(R.id.recyclerView_transactions))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.textView_transactions_error_message))
            .check(matches(withText(errorText)))
            .check(matches(isDisplayed()))
    }

    private fun createFakeTransactions(): Transaction {

        val currencyToDebitInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)
        val currencyToDebitAmount = BigDecimal(1)

        val currencyToCreditInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(10), 0L)
        val currencyToCreditAmount = BigDecimal(2)

        return Transaction(currencyToDebitInfo.currency, currencyToDebitAmount,
                currencyToCreditInfo.currency, currencyToCreditAmount, TransactionType.SELL, 0L)
    }
}
