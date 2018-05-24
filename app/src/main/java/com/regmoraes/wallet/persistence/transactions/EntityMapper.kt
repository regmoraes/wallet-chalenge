package com.regmoraes.wallet.persistence.transactions

import com.wallet.core.transaction.data.Transaction

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object EntityMapper {

    fun toTransactions(transaction: TransactionEntity): Transaction {

        return Transaction(
            transaction.debitCurrency, transaction.debitAmount,
            transaction.creditCurrency, transaction.creditAmount,
            transaction.transactionType, transaction.date
        )
    }

    fun fromTransactions(transaction: Transaction): TransactionEntity {

        return TransactionEntity(
            debitCurrency = transaction.debitCurrency,
            debitAmount = transaction.debitCurrencyAmount,
            creditCurrency = transaction.creditCurrency,
            creditAmount = transaction.creditCurrencyAmount,
            transactionType = transaction.transactionType,
            date = transaction.date
        )
    }
}