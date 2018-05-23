package com.regmoraes.wallet.persistence.transactions

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Entity(tableName = "transactions")
data class TransactionEntity(@PrimaryKey
                             @NotNull
                             @ColumnInfo(name = "uid")
                             val id: String = UUID.randomUUID().toString(),

                             @ColumnInfo(name = "debit_currency")
                             val debitCurrency: String,

                             @ColumnInfo(name = "debit_amount")
                             val debitAmount: String,

                             @ColumnInfo(name = "credit_currency")
                             val creditCurrency: String,

                             @ColumnInfo(name = "credit_amount")
                             val creditAmount: String,

                             @ColumnInfo(name = "operation_type")
                             val operationType: String,

                             @ColumnInfo(name = "instant")
                             val date: Long)