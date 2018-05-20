package com.regmoraes.wallet.storage

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.regmoraes.wallet.storage.transactions.TransactionEntity
import com.regmoraes.wallet.storage.transactions.TransactionsDao
import com.regmoraes.wallet.storage.wallet.WalletEntity
import com.regmoraes.wallet.storage.wallet.WalletsDao
import java.util.concurrent.Executors

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Database(entities = [(WalletEntity::class), (TransactionEntity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionsDao
    abstract fun walletsDao(): WalletsDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =

            Room.databaseBuilder(context,
                AppDatabase::class.java, "wallet_app.db")
                .addCallback(object: Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Executors.newSingleThreadExecutor().execute {

                            getInstance(context).walletsDao().insertAll(WalletEntity.defaultData())
                        }
                    }
                }).build()
    }
}