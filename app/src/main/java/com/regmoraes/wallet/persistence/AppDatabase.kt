package com.regmoraes.wallet.persistence

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.regmoraes.wallet.persistence.converter.BigDecimalConverter
import com.regmoraes.wallet.persistence.converter.CurrencyConverter
import com.regmoraes.wallet.persistence.converter.TransactionTypeConverter
import com.regmoraes.wallet.persistence.transactions.TransactionEntity
import com.regmoraes.wallet.persistence.transactions.TransactionsDao
import com.regmoraes.wallet.persistence.wallet.WalletEntity
import com.regmoraes.wallet.persistence.wallet.WalletsDao
import java.util.concurrent.Executors

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Database(entities = [(WalletEntity::class), (TransactionEntity::class)], version = 1)
@TypeConverters(BigDecimalConverter::class, CurrencyConverter::class, TransactionTypeConverter::class)
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