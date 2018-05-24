package com.regmoraes.wallet.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.toCurrencyEnum
import com.wallet.core.market.data.TransactionType
import com.wallet.core.transaction.toTransactionType
import java.math.BigDecimal


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object TransactionTypeConverter {

    @JvmStatic
    @TypeConverter
    fun fromTransactionType(transactionType: TransactionType): String {
        return transactionType.name
    }

    @JvmStatic
    @TypeConverter
    fun fromTransactionType(string: String): TransactionType {
        return string.toTransactionType()
    }
}