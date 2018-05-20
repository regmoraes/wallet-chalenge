package com.regmoraes.wallet.storage.wallet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.wallet.core.currency.data.Currency
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Entity(tableName = "wallets")
data class WalletEntity(@PrimaryKey
                        @NotNull
                        @ColumnInfo(name = "currency")
                        val currency: String,

                        @ColumnInfo(name = "amount")
                        val amount: String) {

    companion object {

        fun defaultData(): Array<WalletEntity> {

            return arrayOf(
                WalletEntity(Currency.BRL.name, BigDecimal(100000).toString()),
                WalletEntity(Currency.BRITA.name, BigDecimal(0).toString()),
                WalletEntity(Currency.BITCOIN.name, BigDecimal(0).toString())
            )
        }
    }
}

