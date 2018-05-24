package com.regmoraes.wallet.presentation.wallet

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.regmoraes.wallet.R
import com.regmoraes.wallet.RecyclerViewMatcher
import com.regmoraes.wallet.SingleFragmentActivity
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.Wallet
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.allOf
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
class WalletFragmentTests {

    private val walletsResource = MutableLiveData<Resource<List<Wallet>>>()

    @Rule
    @JvmField
    val activityTesRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java)

    @Before
    fun setUp() {

        val fragment = WalletFragment.newInstance()
        val viewModelFactoryMock = mock(WalletViewModelFactory::class.java)
        val viewModelMock = Mockito.mock(WalletViewModel::class.java)

        `when`(viewModelFactoryMock.create(WalletViewModel::class.java)).then { viewModelMock }
        `when`(viewModelMock.getWalletsResource()).then { walletsResource }

        fragment.viewModelFactory = viewModelFactoryMock

        activityTesRule.activity.setFragment(fragment)
    }

    @Test
    fun show_Wallets_correctly() {

        val wallets = listOf(Wallet(Currency.BRL, BigDecimal(50)),
            Wallet(Currency.BRITA, BigDecimal(100)))

        walletsResource.postValue(Resource.success(wallets))

        val recyclerViewMatcher =
            RecyclerViewMatcher(R.id.recyclerView_wallets)

        assertEquals(2, recyclerViewMatcher.getItemsCount())

        val itemPosition = 1
        val item = wallets[itemPosition]

        onView(recyclerViewMatcher.atPositionOnView(itemPosition,
            R.id.textView_currency_name
        ))
            .check(matches(allOf(withText(item.currency.name), isDisplayed())))

        val resources = activityTesRule.activity.resources
        val expectedText = String.format(resources.getString(
            R.string.wallet_balance_format,
            item.amount.toEngineeringString()))

        onView(recyclerViewMatcher.atPositionOnView(itemPosition,
            R.id.textView_currency_balance
        ))
            .check(matches(allOf(withText(expectedText), isDisplayed())))
    }
}
