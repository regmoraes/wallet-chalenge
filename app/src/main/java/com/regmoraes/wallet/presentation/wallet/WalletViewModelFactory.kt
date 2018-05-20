package com.regmoraes.wallet.presentation.wallet

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryViewModel
import com.wallet.core.receipt.ReceiptManager
import com.wallet.core.wallet.WalletManager
import javax.inject.Inject

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
class WalletViewModelFactory @Inject
    constructor(private val walletManager: WalletManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {

            return WalletViewModel(walletManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
