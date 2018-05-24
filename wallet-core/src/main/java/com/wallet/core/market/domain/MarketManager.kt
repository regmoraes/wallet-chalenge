package com.wallet.core.market.domain

import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.data.TransactionType
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.domain.TransactionManager
import com.wallet.core.wallet.domain.WalletManager
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class MarketManager(private val walletManager: WalletManager,
                    private val transactionManager: TransactionManager,
                    private val exchangeCalculator: ExchangeCalculator) {


    fun exchange(from: CurrencyInfo, to: CurrencyInfo, amount: BigDecimal): Single<Transaction> {

        return if(from.price != null && to.price != null) {

            val exchangedAmount = exchangeCalculator.exchange(from.price, to.price, amount)

            executeTransaction(from.currency, amount, to.currency, exchangedAmount, TransactionType.EXCHANGE)

        } else {
            Single.error(IllegalArgumentException("All prices must be != null"))
        }
    }

    fun buy(currencyInfo: CurrencyInfo, amount: BigDecimal): Single<Transaction> {

        val valueToDebit = currencyInfo.price!! * amount

        return executeTransaction(Currency.BRL, valueToDebit,
            currencyInfo.currency, amount, TransactionType.BUY)
    }

    fun sell(currencyInfo: CurrencyInfo, amount: BigDecimal): Single<Transaction> {

        val valueToCredit = currencyInfo.price!! * amount

        return executeTransaction(currencyInfo.currency, amount, Currency.BRL, valueToCredit,
                TransactionType.SELL)
    }

    private fun executeTransaction(currencyToDebit: Currency, amountToDebit: BigDecimal,
                                   currencyToCredit: Currency, amountToCredit: BigDecimal,
                                   transactionType: TransactionType) : Single<Transaction> {

        return walletManager.debit(currencyToDebit, amountToDebit)
            .andThen(walletManager.credit(currencyToCredit, amountToCredit))
            .andThen(transactionManager.createTransactions(currencyToDebit, amountToDebit, currencyToCredit,
                amountToCredit, transactionType))
    }
}