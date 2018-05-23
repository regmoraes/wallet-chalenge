package com.regmoraes.wallet.presentation.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.regmoraes.wallet.BR
import com.regmoraes.wallet.R
import com.regmoraes.wallet.databinding.AdapterTransactionHistoryItemBinding
import com.wallet.core.transaction.data.Transaction


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class TransactionsHistoryAdapter : RecyclerView.Adapter<TransactionsHistoryAdapter.ViewHolder>() {

    private var transactions: List<Transaction>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_transaction_history_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holderListItem: ViewHolder, position: Int) {

        holderListItem.binding.setVariable(BR.transaction, transactions?.get(position))
        holderListItem.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = transactions?.size ?: 0

    fun setData(data: List<Transaction>?) {
        transactions = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding: AdapterTransactionHistoryItemBinding = AdapterTransactionHistoryItemBinding.bind(itemView)
    }
}