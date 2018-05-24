package com.regmoraes.wallet.persistence.wallet

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
                        @ColumnInfo(name = "code")
                        val currency: Currency,

                        @ColumnInfo(name = "amount")
                        val amount: BigDecimal) {

    companion object {

        fun defaultData(): Array<WalletEntity> {

            return arrayOf(
                WalletEntity(Currency.BRL, BigDecimal(100000)),
                WalletEntity(Currency.BRITA, BigDecimal(0)),
                WalletEntity(Currency.BITCOIN, BigDecimal(0))
            )
        }
    }
}

