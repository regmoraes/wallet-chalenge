//package com.regmoraes.wallet.presentation.prices
//
//import android.arch.lifecycle.ViewModel
//import android.arch.lifecycle.ViewModelProvider
//import com.regmoraes.wallet.presentation.transactions.TransactionsHistoryViewModel
//import com.wallet.core.currency.CurrencyManager
//import javax.inject.Inject
//
///**
// * Copyright {2018} {Rômulo Eduardo G. Moraes}
// */
//class PricesViewModelFactory @Inject
//    constructor(private val currencyInfoManager: CurrencyManager) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(TransactionsHistoryViewModel::class.java)) {
//
//            return TransactionsHistoryViewModel(
//                currencyInfoManager
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
