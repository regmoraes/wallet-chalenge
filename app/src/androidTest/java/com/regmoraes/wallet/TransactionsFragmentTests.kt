package com.regmoraes.wallet

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryFragment
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryViewModel
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryViewModelFactory
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.OperationType
import com.wallet.core.receipt.Receipt
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


    private val receiptsResource = MutableLiveData<Resource<List<Receipt>>>()

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
        `when`(viewModelMock.getReceiptsResource()).then { receiptsResource }

        fragment.viewModelFactory = viewModelFactoryMock

        activityTesRule.activity.setFragment(fragment)
    }

    @Test
    fun show_Transaction_Receipt_Correctly() {

        val receipt = createFakeReceipt()

        receiptsResource.postValue(Resource.success(listOf(receipt)))

        onView(withId(R.id.textView_credited_amount))
            .check(matches(withText(receipt.creditCurrencyAmount.toPlainString())))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_currency_credited))
            .check(matches(withText(receipt.creditCurrency.name)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_debited_amount))
            .check(matches(withText(receipt.debitCurrencyAmount.toPlainString())))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_currency_debited))
            .check(matches(withText(receipt.debitCurrency.name)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun show_Message_When_Empty_Receipts() {

        receiptsResource.postValue(Resource.success(emptyList()))

        val errorText = activityTesRule.activity.getString(R.string.empty_receipts)

        onView(withId(R.id.recyclerView_receipts))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.textView_receipts_error_message))
            .check(matches(withText(errorText)))
            .check(matches(isDisplayed()))
    }

    private fun createFakeReceipt(): Receipt {

        val currencyToDebitInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)
        val currencyToDebitAmount = BigDecimal(1)

        val currencyToCreditInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(10), 0L)
        val currencyToCreditAmount = BigDecimal(2)

        return Receipt(currencyToDebitInfo.currency, currencyToDebitAmount,
            currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.SELL, 0L)
    }
}
