package com.regmoraes.wallet.presentation.wallet

import com.regmoraes.wallet.presentation.BaseTest
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.util.testObserver
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.wallet.data.Wallet
import com.wallet.core.wallet.domain.WalletManager
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.math.BigDecimal
import kotlin.test.assertTrue

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletViewModelTests: BaseTest() {

    @Mock
    lateinit var walletManager: WalletManager

    private lateinit var viewModel: WalletViewModel

    override fun setUp() {
        super.setUp()
        viewModel = WalletViewModel(walletManager)
    }

    @Test
    fun getWallets_emitsCorrectResources() {

        val walletOne = Wallet(Currency.BRL, BigDecimal(200))
        val walletTwo = Wallet(Currency.BRITA, BigDecimal(200))

        val wallets = listOf(walletOne, walletTwo)

        val expectedResources =
                listOf(Resource.loading(), Resource.success(wallets))

        val viewModelObserver=
                viewModel.getWalletsResource().testObserver()

        `when`(walletManager.getWallets()).then {
            Flowable.just(wallets)
        }

        viewModel.getWallets()

        verify(walletManager, times(1)).getWallets()

        assertTrue(expectedResources == viewModelObserver.observedValues)
    }
}
