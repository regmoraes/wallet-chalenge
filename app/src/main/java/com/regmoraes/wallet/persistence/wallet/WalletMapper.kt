package com.regmoraes.wallet.persistence.wallet

import com.wallet.core.currency.data.toCurrencyEnum
import com.wallet.core.wallet.data.Wallet
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object WalletMapper {

    fun toWallet(walletEntity: WalletEntity): Wallet {

        val currency = walletEntity.currency.toCurrencyEnum()
        val amount = BigDecimal(walletEntity.amount)

        return Wallet(currency, amount)
    }

    fun fromWallet(wallet: Wallet): WalletEntity {

        return WalletEntity(wallet.currency.name, wallet.amount.toPlainString())
    }
}