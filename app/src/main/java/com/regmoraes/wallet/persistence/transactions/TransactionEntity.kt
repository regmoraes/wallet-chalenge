package com.regmoraes.wallet.persistence.transactions

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.wallet.core.currency.data.Currency
import com.wallet.core.market.data.TransactionType
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Entity(tableName = "transactions")
data class TransactionEntity(@PrimaryKey(autoGenerate = true)
                             @NotNull
                             @ColumnInfo(name = "uid")
                             val id: Long = 0,

                             @ColumnInfo(name = "debit_currency")
                             val debitCurrency: Currency,

                             @ColumnInfo(name = "debit_amount")
                             val debitAmount: BigDecimal,

                             @ColumnInfo(name = "credit_currency")
                             val creditCurrency: Currency,

                             @ColumnInfo(name = "credit_amount")
                             val creditAmount: BigDecimal,

                             @ColumnInfo(name = "operation_type")
                             val transactionType: TransactionType,

                             @ColumnInfo(name = "instant")
                             val date: Long)