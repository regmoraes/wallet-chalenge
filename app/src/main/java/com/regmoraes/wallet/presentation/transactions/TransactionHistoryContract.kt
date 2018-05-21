package com.regmoraes.wallet.presentation.transactions

import android.arch.lifecycle.MutableLiveData
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.receipt.Receipt

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
interface TransactionHistoryContract {

    interface ViewModel {
        val receiptsResource: MutableLiveData<Resource<List<Receipt>>>
    }

    interface View {

        fun showReceipts(receipt: List<Receipt>?)

        fun showEmptyReceiptsMessage()
    }
}