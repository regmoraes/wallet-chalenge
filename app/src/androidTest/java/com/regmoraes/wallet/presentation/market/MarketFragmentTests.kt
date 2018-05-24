package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.R
import com.regmoraes.wallet.RecyclerViewMatcher
import com.regmoraes.wallet.SingleFragmentActivity
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.SingleLiveEvent
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.math.BigDecimal


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MarketFragmentTests {

    private val currenciesInfoResource = MutableLiveData<Resource<List<CurrencyInfo>>>()
    private val transactionFinishedEvent = SingleLiveEvent<Resource<Boolean>>()


    @Rule
    @JvmField
    val activityTesRule = ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java)

    @Before
    fun setUp() {

        val fragment = MarketFragment.newInstance()
        val viewModelMock = mock(MarketViewModel::class.java)
        val viewModelFactoryMock = mock(MarketViewModelFactory::class.java)

        `when`(viewModelFactoryMock.create(MarketViewModel::class.java)).then { viewModelMock }
        `when`(viewModelMock.getCurrencyInfoResource()).then { currenciesInfoResource }
        `when`(viewModelMock.getTransactionFinishedEvent()).then { transactionFinishedEvent }

        fragment.viewModelFactory = viewModelFactoryMock

        activityTesRule.activity.replaceFragment(fragment)
    }

    @Test
    fun show_correct_marketListItemInfo() {

        val baseCurrencyInfo = CurrencyInfo(Currency.BRL, BigDecimal(1), 0L)
        val currencyInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)

        currenciesInfoResource.postValue(Resource.success(listOf(currencyInfo)))

        val expectedName =
                String.format(
                        activityTesRule.activity.getString(R.string.market_currency_title_format),
                        currencyInfo.currency.name, baseCurrencyInfo.currency.name)

        val expectedPrice =
            String.format(
                activityTesRule.activity.getString(R.string.market_currency_price_format),
                '$', currencyInfo.price?.toEngineeringString())

        onView(withId(R.id.textView_currencyInfo_name))
                .check(matches(withText(expectedName)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.textView_currencyInfo_price))
                .check(matches(withText(expectedPrice)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.button_sell))
                .check(matches(withText(R.string.sell)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.button_buy))
                .check(matches(withText(R.string.buy)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.button_exchange))
                .check(matches(withText(R.string.exchange)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun show_correct_dialogInfo_whenClickTo_buy() {

        val resources = activityTesRule.activity.resources
        val currencyInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)

        val expectedHint =
                String.format(resources.getString(
                    R.string.transaction_pending_amount_hint_format,
                        TransactionType.BUY))

        currenciesInfoResource.postValue(Resource.success(listOf(currencyInfo)))

        onView(withId(R.id.button_buy)).perform(click())

        onView(withId(R.id.editText_amount))
                .check(matches(allOf(withHint(expectedHint), isDisplayed())))

        onView(withId(R.id.button_cancel))
                .check(matches(isDisplayed()))

        onView(withId(R.id.button_confirm))
                .check(matches(isDisplayed()))
    }

    @Test
    fun show_correct_dialogInfo_whenClickTo_sell() {

        val resources = activityTesRule.activity.resources
        val currencyInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(30), 0L)

        currenciesInfoResource.postValue(Resource.success(listOf(currencyInfo)))

        val expectedTitle =
                String.format(resources.getString(
                    R.string.transaction_pending_sell_format,
                        currencyInfo.currency.name))

        val expectedHint =
                String.format(resources.getString(
                    R.string.transaction_pending_amount_hint_format,
                        TransactionType.SELL))

        onView(withId(R.id.button_sell)).perform(click())

        onView(withId(R.id.editText_amount))
                .check(matches(allOf(withHint(expectedHint), isDisplayed())))

        onView(withId(R.id.textView_title))
                .check(matches(allOf(withText(expectedTitle), isDisplayed())))

        onView(withId(R.id.button_cancel))
                .check(matches(isDisplayed()))

        onView(withId(R.id.button_confirm))
                .check(matches(isDisplayed()))
    }

    @Test
    fun show_correct_dialogInfo_whenClickTo_exchange() {

        val resources = activityTesRule.activity.resources
        val fromCurrencyInfo = CurrencyInfo(Currency.BITCOIN, BigDecimal(30), 0L)
        val toCurrencyInfo = CurrencyInfo(Currency.BRITA, BigDecimal(40), 0L)

        currenciesInfoResource.postValue(Resource.success(listOf(fromCurrencyInfo, toCurrencyInfo)))

        val expectedTitle =
                String.format(resources.getString(
                    R.string.transaction_pending_exchange_format,
                        fromCurrencyInfo.currency.name, toCurrencyInfo.currency.name))

        val expectedHint =
                String.format(resources.getString(
                    R.string.transaction_pending_amount_hint_format,
                        TransactionType.EXCHANGE))

        val itemPosition  = 0

        onView((RecyclerViewMatcher(R.id.recyclerView_market)
                .atPositionOnView(itemPosition, R.id.button_exchange)))
                .perform(click())

        onView(withId(R.id.editText_amount))
                .check(matches(allOf(withHint(expectedHint), isDisplayed())))

        onView(withId(R.id.textView_title))
                .check(matches(allOf(withText(expectedTitle), isDisplayed())))

        onView(withId(R.id.button_cancel))
                .check(matches(isDisplayed()))

        onView(withId(R.id.button_confirm))
                .check(matches(isDisplayed()))
    }

    @Test
    fun show_errorMessageIf_currencyInfo_fetchFails() {

        val currencyInfo = CurrencyInfo(Currency.BITCOIN, null, null)

        currenciesInfoResource.postValue(Resource.success(listOf(currencyInfo)))

        onView((RecyclerViewMatcher(R.id.recyclerView_market)
            .atPositionOnView(0, R.id.button_exchange)))
            .check(matches(not(isDisplayed())))

        onView((RecyclerViewMatcher(R.id.recyclerView_market)
            .atPositionOnView(0, R.id.button_buy)))
            .check(matches(not(isDisplayed())))

        onView((RecyclerViewMatcher(R.id.recyclerView_market)
            .atPositionOnView(0, R.id.button_sell)))
            .check(matches(not(isDisplayed())))

        onView((RecyclerViewMatcher(R.id.recyclerView_market)
            .atPositionOnView(0, R.id.textView_currencyInfo_price)))
            .check(matches(not(isDisplayed())))

        onView((RecyclerViewMatcher(R.id.recyclerView_market)
            .atPositionOnView(0, R.id.textView_currencyInfo_error)))
            .check(matches(allOf(
                withText(R.string.market_currency_fetch_error),
                isDisplayed())))
    }
}
