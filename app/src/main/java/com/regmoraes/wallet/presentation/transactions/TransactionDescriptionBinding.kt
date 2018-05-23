package com.regmoraes.wallet.presentation.transactions

import android.databinding.BindingAdapter
import android.widget.TextView
import com.regmoraes.wallet.R
import com.wallet.core.market.data.OperationType
import com.wallet.core.transaction.data.Transaction


/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
object TransactionDescriptionBinding {

    @JvmStatic
    @BindingAdapter("databind:transactionDescription")
    fun bindDateTime(view: TextView, transaction: Transaction) {

        val context = view.context

        val creditCurrencyName = transaction.creditCurrency.name
        val debitCurrencyName = transaction.debitCurrency.name

        view.text = when(transaction.operationType) {

            OperationType.BUY ->
                String.format(context.getString(R.string.transaction_buy_format),
                    creditCurrencyName, debitCurrencyName)

            OperationType.SELL -> String.format(context.getString(R.string.transaction_sell_format),
                debitCurrencyName, creditCurrencyName)

            OperationType.EXCHANGE -> String.format(context.getString(R.string.transaction_exchange_format),
                debitCurrencyName, creditCurrencyName)
        }
    }
}
