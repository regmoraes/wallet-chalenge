package com.wallet.core.market

import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.receipt.Receipt
import com.wallet.core.receipt.ReceiptManager
import com.wallet.core.wallet.WalletManager
import io.reactivex.Single
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class MarketManager(private val walletManager: WalletManager,
                    private val receiptManager: ReceiptManager,
                    private val exchangeCalculator: ExchangeCalculator) {


    fun exchange(from: CurrencyInfo, to: CurrencyInfo, amount: BigDecimal): Single<Receipt> {

        return if(from.price != null && to.price != null) {

            val exchangedAmount = exchangeCalculator.exchange(from.price, to.price, amount)

            executeTransaction(from.currency, amount, to.currency, exchangedAmount, OperationType.EXCHANGE)

        } else {
            Single.error(IllegalArgumentException("All prices must be != null"))
        }
    }

    fun buy(currencyInfo: CurrencyInfo, amount: BigDecimal): Single<Receipt> {

        val valueToDebit = currencyInfo.price!! * amount

        return executeTransaction(Currency.BRL, valueToDebit,
            currencyInfo.currency, amount, OperationType.BUY)
    }

    fun sell(currencyInfo: CurrencyInfo, amount: BigDecimal): Single<Receipt> {

        val valueToCredit = currencyInfo.price!! * amount

        return executeTransaction(currencyInfo.currency, amount, Currency.BRL, valueToCredit,
            OperationType.SELL)
    }

    private fun executeTransaction(currencyToDebit: Currency, amountToDebit: BigDecimal,
                                   currencyToCredit: Currency, amountToCredit: BigDecimal,
                                   operationType: OperationType) : Single<Receipt> {

        return walletManager.debit(currencyToDebit, amountToDebit)
            .andThen(walletManager.credit(currencyToCredit, amountToCredit))
            .andThen(receiptManager.createReceipt(currencyToDebit, amountToDebit, currencyToCredit,
                amountToCredit, operationType))
    }
}