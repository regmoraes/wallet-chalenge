package com.regmoraes.wallet.presentation.market

import com.regmoraes.wallet.presentation.BaseTest
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.util.testObserver
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.currency.domain.CurrencyManager
import com.wallet.core.market.domain.MarketManager
import io.reactivex.Flowable
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.math.BigDecimal
import kotlin.test.assertTrue

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class MarketViewModelTests: BaseTest() {

    @Mock
    lateinit var marketManager: MarketManager
    @Mock
    lateinit var currencyManager: CurrencyManager

    private lateinit var viewModel: MarketViewModel

    override fun setUp() {
        super.setUp()
        viewModel = MarketViewModel(marketManager, currencyManager)
    }

    @Test
    fun getAllCurrenciesPriceToday_emitsCorrectResources() {

        val currencyInfoOne =
                CurrencyInfo(Currency.BRL, BigDecimal(1), 0L)

        val currencyInfoTwo =
                CurrencyInfo(Currency.BRITA, BigDecimal(2), 0L)

        val currenciesInfo = listOf(currencyInfoOne, currencyInfoTwo)

        val expectedResources =
                mutableListOf(
                        Resource.loading(),
                        Resource.success(listOf(currencyInfoOne, currencyInfoTwo)),
                        Resource.success(listOf(currencyInfoOne, currencyInfoTwo))
                )

        val viewModelObserver=
                viewModel.getCurrencyInfoResource().testObserver()

        `when`(currencyManager.getAllCurrenciesInfo(anyLong())).then {
            Flowable.fromIterable(currenciesInfo)
        }

        viewModel.getAllCurrenciesTodayPrice()

        verify(currencyManager, times(1)).getAllCurrenciesInfo(anyLong())

        assertTrue(viewModelObserver.observedValues == expectedResources)
    }
}
