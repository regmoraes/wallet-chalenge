package com.regmoraes.wallet.persistence.transactions

import com.wallet.core.currency.data.toCurrencyEnum
import com.wallet.core.receipt.data.Receipt
import com.wallet.core.receipt.toTransactionType
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object TransactionMapper {

    fun toReceipt(transaction: TransactionEntity): Receipt {

        return Receipt(transaction.debitCurrency.toCurrencyEnum(), BigDecimal(transaction.debitAmount),
                transaction.creditCurrency.toCurrencyEnum(), BigDecimal(transaction.creditAmount),
                transaction.operationType.toTransactionType(), transaction.date)
    }

    fun fromReceipt(receipt: Receipt): TransactionEntity {

        return TransactionEntity(
            debitCurrency = receipt.debitCurrency.name,
            debitAmount = receipt.debitCurrencyAmount.toPlainString(),
            creditCurrency = receipt.creditCurrency.name,
            creditAmount = receipt.creditCurrencyAmount.toPlainString(),
            operationType = receipt.operationType.name,
            date = receipt.date)
    }
}