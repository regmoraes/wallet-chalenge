package com.regmoraes.wallet.presentation.wallet

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.regmoraes.wallet.databinding.AdapterWalletItemBinding
import com.wallet.core.wallet.data.Wallet


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletsAdapter : RecyclerView.Adapter<WalletsAdapter.ViewHolder>() {

    private var wallets: List<Wallet>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(viewGroup.context)
        val viewBinding = AdapterWalletItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holderListItem: ViewHolder, position: Int) {

        holderListItem.bind(wallets?.get(position))
    }

    override fun getItemCount(): Int = wallets?.size ?: 0

    fun setData(data: List<Wallet>?) {
        wallets = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewBinding: AdapterWalletItemBinding)
        : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(wallet: Wallet?) {
            viewBinding.wallet = wallet
            viewBinding.executePendingBindings()
        }
    }
}