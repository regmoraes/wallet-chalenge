package com.wallet.core.wallet

import com.wallet.core.BaseTest
import com.wallet.core.currency.data.Currency
import com.wallet.core.wallet.data.Wallet
import com.wallet.core.wallet.data.WalletRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletManagerTests : BaseTest() {

    @Mock
    lateinit var walletRepositoryMock: WalletRepository

    private lateinit var walletManager: WalletManager

    override fun setUp() {
        super.setUp()
        walletManager = WalletManager(Currency.BRL, walletRepositoryMock)
    }

    @Test
    fun callTo_debit_shouldCall_debit_inRepository() {

        val wallet = Wallet(Currency.BITCOIN, BigDecimal(100))

        val amountToDebit = BigDecimal(10)

        `when`(walletRepositoryMock.debit(wallet.currency, amountToDebit)).then { Completable.complete() }
        `when`(walletRepositoryMock.getWallet(wallet.currency)).then { Single.just(wallet) }

        walletManager.debit(wallet.currency, amountToDebit)
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()

        verify(walletRepositoryMock, times(1)).debit(wallet.currency, amountToDebit)
        verify(walletRepositoryMock, never()).credit(any(), any())
    }

    @Test
    fun callTo_debit_whenNoEnoughMoney_shouldNotCall_debit_inRepository() {

        val amountToDebit = BigDecimal(100)

        val wallet = Wallet(Currency.BITCOIN, BigDecimal(10))

        `when`(walletRepositoryMock.getWallet(wallet.currency)).then { Single.just(wallet) }

        walletManager.debit(wallet.currency, amountToDebit)
            .test()
            .await()
            .assertError(InsufficientFundsException::class.java)
            .assertNotComplete()

        verify(walletRepositoryMock, never()).debit(any(), any())
        verify(walletRepositoryMock, never()).credit(any(), any())
    }

    @Test
    fun callTo_credit_shouldCall_credit_inRepository() {

        val currency = Currency.BRL
        val amountToCredit = BigDecimal(10)

        `when`(walletRepositoryMock.credit(currency, amountToCredit)).then { Completable.complete() }

        walletManager.credit(currency, amountToCredit)
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()

        verify(walletRepositoryMock, times(1)).credit(currency, amountToCredit)
        verify(walletRepositoryMock, never()).debit(any(), any())
    }
}