package com.regmoraes.wallet.presentation.transactions

import android.databinding.BindingAdapter
import android.widget.TextView
import com.regmoraes.wallet.R
import com.wallet.core.market.OperationType


/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
object TransactionTypeBinding {

    @JvmStatic
    @BindingAdapter("databind:transactionType")
    fun bindDateTime(view: TextView, transactionType: OperationType) {

        val context = view.context

        view.text = when(transactionType) {

            OperationType.BUY -> context.getString(R.string.buy)
            OperationType.SELL -> context.getString(R.string.sell)
            OperationType.EXCHANGE -> context.getString(R.string.exchange)
        }
    }
}
