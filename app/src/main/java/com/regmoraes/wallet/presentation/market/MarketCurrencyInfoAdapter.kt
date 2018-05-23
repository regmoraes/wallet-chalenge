package com.regmoraes.wallet.presentation.market

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.regmoraes.wallet.BR
import com.regmoraes.wallet.R
import com.regmoraes.wallet.databinding.AdapterMarketItemBinding
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.OperationType


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class MarketCurrencyInfoAdapter(private val listener: OnItemClickListener)
    : RecyclerView.Adapter<MarketCurrencyInfoAdapter.ViewHolder>() {

    private var currencies: List<CurrencyInfo>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_market_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holderListItem: ViewHolder, position: Int) {

        holderListItem.binding.setVariable(BR.baseCurrency, Currency.BRL)
        holderListItem.binding.setVariable(BR.currencyInfo, currencies?.get(position))
        holderListItem.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = currencies?.size ?: 0

    fun setData(data: List<CurrencyInfo>?) {
        currencies = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val binding: AdapterMarketItemBinding = AdapterMarketItemBinding.bind(itemView)

        init {
            binding.buttonBuy.setOnClickListener(this)
            binding.buttonSell.setOnClickListener(this)
            binding.buttonExchange.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            val currencyInfo = currencies?.get(adapterPosition)

            if(currencyInfo != null) {
                when (view?.id) {

                    R.id.button_sell -> listener.onOperationClicked(
                        currencyInfo,
                        OperationType.SELL
                    )
                    R.id.button_buy -> listener.onOperationClicked(currencyInfo, OperationType.BUY)
                    R.id.button_exchange -> listener.onOperationClicked(currencyInfo, OperationType.EXCHANGE
                    )
                }
            }
        }

    }

    interface OnItemClickListener {

        fun onOperationClicked(currencyInfo: CurrencyInfo, operationType: OperationType)
    }
}
