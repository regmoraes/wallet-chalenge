package com.regmoraes.wallet.storage.transactions

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions")
    fun getTransactions(): Flowable<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: TransactionEntity)
}