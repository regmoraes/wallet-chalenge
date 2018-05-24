package com.wallet.core.transaction.domain

import com.wallet.core.currency.data.Currency
import com.wallet.core.market.data.TransactionType
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.data.TransactionRepository
import io.reactivex.Flowable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class TransactionManager(private val transactionRepository: TransactionRepository) {

    fun getTransactions(): Flowable<List<Transaction>> {

        return transactionRepository.getTransactions()
    }



    fun createTransactions(debitCurrency: Currency,
                           debitCurrencyAmount: BigDecimal,
                           creditCurrency: Currency,
                           creditCurrencyAmount: BigDecimal,
                           transactionType: TransactionType): Single<Transaction> {

        val transaction = Transaction(debitCurrency, debitCurrencyAmount, creditCurrency,
                creditCurrencyAmount, transactionType, Calendar.getInstance().timeInMillis)

        return transactionRepository.saveTransactions(transaction).toSingleDefault(transaction)
    }
}