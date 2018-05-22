package com.regmoraes.wallet

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.market.MarketFragment
import com.regmoraes.wallet.presentation.market.MarketViewModel
import com.regmoraes.wallet.presentation.market.MarketViewModelFactory
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
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

    private val currencyInfoResource = MutableLiveData<Resource<List<CurrencyInfo>>>()
    private val walletBaseCurrencyAmountResource = MutableLiveData<Resource<BigDecimal>>()

    @Rule
    @JvmField
    val activityTesRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java)

    @Before
    fun setUp() {

        val fragment = MarketFragment.newInstance()
        val viewModelMock = mock(MarketViewModel::class.java)
        val viewModelFactoryMock = mock(MarketViewModelFactory::class.java)

        `when`(viewModelFactoryMock.create(MarketViewModel::class.java)).then { viewModelMock }
        `when`(viewModelMock.getCurrencyInfoResource()).then { currencyInfoResource }
        `when`(viewModelMock.getWalletBaseCurrencyAmountResource()).then { walletBaseCurrencyAmountResource }

        fragment.viewModelFactory = viewModelFactoryMock

        activityTesRule.activity.setFragment(fragment)
    }

    @Test
    fun show_Market_Item_Correctly() {

        val currencyInfo = CurrencyInfo(Currency.BRITA, BigDecimal(50), 0L)

        currencyInfoResource.postValue(Resource.success(listOf(currencyInfo)))

        val expectedText =
            String.format(
                activityTesRule.activity.getString(R.string.currency_price_format),
                currencyInfo.currency.name, Currency.BRL
            )

        onView(withId(R.id.textView_currencyInfo_name))
            .check(matches(withText(expectedText)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textView_currencyInfo_price))
            .check(matches(withText(currencyInfo.price?.toEngineeringString())))
            .check(matches(isDisplayed()))

        onView(withId(R.id.button_sell))
            .check(matches(isDisplayed()))

        onView(withId(R.id.button_buy))
            .check(matches(isDisplayed()))

        onView(withId(R.id.button_exchange))
            .check(matches(isDisplayed()))
    }
}
