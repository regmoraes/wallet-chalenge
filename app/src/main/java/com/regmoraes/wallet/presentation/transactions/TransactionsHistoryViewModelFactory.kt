package com.regmoraes.wallet.presentation.transactions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wallet.core.transaction.domain.TransactionManager
import javax.inject.Inject

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 */
open class TransactionsHistoryViewModelFactory @Inject
constructor(private val transactionManager: TransactionManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsHistoryViewModel::class.java)) {

            return TransactionsHistoryViewModel(transactionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
