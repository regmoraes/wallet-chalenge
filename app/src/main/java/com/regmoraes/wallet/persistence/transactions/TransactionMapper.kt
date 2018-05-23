package com.regmoraes.wallet.persistence.transactions

import com.wallet.core.currency.data.toCurrencyEnum
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.toTransactionType
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object TransactionMapper {

    fun toTransactions(transaction: TransactionEntity): Transaction {

        return Transaction(transaction.debitCurrency.toCurrencyEnum(), BigDecimal(transaction.debitAmount),
                transaction.creditCurrency.toCurrencyEnum(), BigDecimal(transaction.creditAmount),
                transaction.operationType.toTransactionType(), transaction.date)
    }

    fun fromTransactions(transaction: Transaction): TransactionEntity {

        return TransactionEntity(
            debitCurrency = transaction.debitCurrency.name,
            debitAmount = transaction.debitCurrencyAmount.toPlainString(),
            creditCurrency = transaction.creditCurrency.name,
            creditAmount = transaction.creditCurrencyAmount.toPlainString(),
            operationType = transaction.operationType.name,
            date = transaction.date)
    }
}