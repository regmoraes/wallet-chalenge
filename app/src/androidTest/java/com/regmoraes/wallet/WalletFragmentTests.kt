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
import com.regmoraes.wallet.presentation.wallet.WalletFragment
import com.regmoraes.wallet.presentation.wallet.WalletViewModel
import com.regmoraes.wallet.presentation.wallet.WalletViewModelFactory
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.OperationType
import com.wallet.core.receipt.Receipt
import com.wallet.core.wallet.data.Wallet
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.allOf
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

        val recyclerViewMatcher = RecyclerViewMatcher(R.id.recyclerView_wallets)

        assertEquals(2, recyclerViewMatcher.getItemsCount())

        val itemPosition = 1

        onView(recyclerViewMatcher.atPositionOnView(itemPosition, R.id.textView_currency_name))
            .check(matches(allOf(withText(Currency.BRITA.name), isDisplayed())))

        onView(recyclerViewMatcher.atPositionOnView(itemPosition, R.id.textView_currency_balance))
            .check(matches(allOf(withText(BigDecimal(100).toEngineeringString()), isDisplayed())))
    }
}
