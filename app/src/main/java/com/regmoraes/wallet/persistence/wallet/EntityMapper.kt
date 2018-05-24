package com.regmoraes.wallet.persistence.wallet

import com.wallet.core.wallet.data.Wallet

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object EntityMapper {

    fun toWallet(walletEntity: WalletEntity): Wallet {
        return Wallet(walletEntity.currency, walletEntity.amount)
    }

    fun fromWallet(wallet: Wallet): WalletEntity {
        return WalletEntity(wallet.currency, wallet.amount)
    }
}