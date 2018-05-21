package com.regmoraes.wallet.presentation.market

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.regmoraes.wallet.BR
import com.regmoraes.wallet.R
import com.regmoraes.wallet.databinding.AdapterMarketItemBinding
import com.regmoraes.wallet.databinding.AdapterTransactionHistoryItemBinding
import com.wallet.core.currency.data.CurrencyInfo


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class MarketCurrencyInfoAdapter : RecyclerView.Adapter<MarketCurrencyInfoAdapter.ViewHolder>() {

    private var currencies: List<CurrencyInfo>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_market_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holderListItem: ViewHolder, position: Int) {

        holderListItem.binding.setVariable(BR.currencyInfo, currencies?.get(position))
        holderListItem.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = currencies?.size ?: 0

    fun setData(data: List<CurrencyInfo>?) {
        currencies = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = AdapterMarketItemBinding.bind(itemView)
    }
}