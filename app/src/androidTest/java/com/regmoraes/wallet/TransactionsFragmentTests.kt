package com.regmoraes.wallet

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.di.StorageTestModule
import com.regmoraes.wallet.di.component.DaggerAppComponent
import com.regmoraes.wallet.di.module.AndroidModule
import com.regmoraes.wallet.presentation.market.MarketActivity
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryFragment
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.OperationType
import com.wallet.core.receipt.Receipt
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TransactionsFragmentTests {

    private var receipts = mutableListOf<Receipt>()

    @Rule
    @JvmField
    val activityTesRule: ActivityTestRule<MarketActivity> =
        object: ActivityTestRule<MarketActivity>(MarketActivity::class.java) {

            override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()

                val appContext = (InstrumentationRegistry.getInstrumentation()
                    .targetContext.applicationContext as WalletApp)

                val testComponent = DaggerAppComponent
                    .builder()
                    .storageModule(StorageTestModule(receipts))
                    .androidModule(AndroidModule(appContext))
                    .build()

                appContext.appComponent = testComponent
            }
        }

    @Before
    fun setUp() {

        val transactionsHistoryFragment = TransactionsHistoryFragment.newInstance()
        activityTesRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.content, transactionsHistoryFragment)
            .commit()
    }

    @After
    fun cleanUp () {
        receipts.clear()
    }

    @Test
    fun show_Transaction_Receipt_Correctly() {

        val receipt = createFakeReceipt()

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

        val receipt = Receipt(currencyToDebitInfo.currency, currencyToDebitAmount,
            currencyToCreditInfo.currency, currencyToCreditAmount, OperationType.SELL, 0L)

        receipts.add(receipt)

        return receipt
    }
}
