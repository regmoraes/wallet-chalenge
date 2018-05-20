package com.regmoraes.wallet.storage.wallet

import com.wallet.core.currency.toCurrency
import com.wallet.core.wallet.data.Wallet
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object WalletMapper {

    fun toWallet(walletEntity: WalletEntity): Wallet {

        val currency = walletEntity.currency.toCurrency()
        val amount = BigDecimal(walletEntity.amount)

        return Wallet(currency, amount)
    }

    fun fromWallet(wallet: Wallet): WalletEntity {

        return WalletEntity(wallet.currency.name, wallet.amount.toPlainString())
    }
}