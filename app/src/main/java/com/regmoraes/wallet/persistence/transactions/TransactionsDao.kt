package com.regmoraes.wallet.persistence.transactions

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions ORDER BY uid DESC")
    fun getTransactions(): Flowable<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(transaction: Array<TransactionEntity>)
}