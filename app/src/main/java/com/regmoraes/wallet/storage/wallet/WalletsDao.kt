package com.regmoraes.wallet.storage.wallet

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface WalletsDao {

    @Query("SELECT * FROM wallets")
    fun getWallets(): Single<List<WalletEntity>>

    @Query("SELECT * FROM wallets WHERE code = :currency")
    fun getWalletByCurrency(currency: String): Single<WalletEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(walletEntity: WalletEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(walletEntity: Array<WalletEntity>)
}