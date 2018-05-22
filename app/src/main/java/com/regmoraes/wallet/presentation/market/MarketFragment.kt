package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.regmoraes.wallet.WalletApp
import com.regmoraes.wallet.databinding.FragmentMarketBinding
import com.regmoraes.wallet.di.component.ViewComponent
import com.regmoraes.wallet.presentation.Status
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.currency.toCurrencyEnum
import com.wallet.core.market.OperationType
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class MarketFragment : Fragment(), MarketCurrencyInfoAdapter.OnItemClickListener,
    TransactionConfirmationDialogFragment.OnDialogFragmentClicked {

    private var component: ViewComponent? = null
    private lateinit var viewBinding: FragmentMarketBinding

    @Inject lateinit var viewModelFactory: MarketViewModelFactory
    lateinit var viewModel: MarketViewModel
    var adapter = MarketCurrencyInfoAdapter(this)

    private lateinit var pendingTransaction: PendingTransaction

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewBinding = FragmentMarketBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        viewBinding.recyclerViewMarket.layoutManager = layoutManager
        viewBinding.recyclerViewMarket.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        component = (activity?.application as WalletApp).appComponent.marketComponent()
        component?.inject(this)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MarketViewModel::class.java)

        viewModel.currenciesInfoResource.observe(activity!!, Observer { resource ->

            if(resource != null) {

                when (resource.status) {

                    Status.SUCCESS -> {

                        adapter.setData(resource.data)
                    }
                }
            }
        })

//        viewModel.walletBaseCurrencyAmountResource.observe(this, Observer { resource ->
//
//            if(resource != null) {
//
//                when(resource.status) {
//
//                    Status.SUCCESS -> {
//
//                        viewBinding.textViewWalletTotalAmount.text =
//                                String.format(getString(R.string.amount), resource.data.toString())
//                    }
//
//                    Status.ERROR -> {
//                        Toast.makeText(context, resource.error?.message, Toast.LENGTH_SHORT)
//                            .show()
//                    }
//
//                    else -> {}
//                }
//            }
//        })
    }



    override fun onOperationClicked(currencyInfo: CurrencyInfo, operationType: OperationType) {

        pendingTransaction = PendingTransaction(currencyInfo = currencyInfo,
            operationType = operationType)

        val transactionDialog = TransactionConfirmationDialogFragment.newInstance(currencyInfo.currency.name, operationType.name)
        transactionDialog.setTargetFragment(this, 300)
        transactionDialog.show(activity!!.supportFragmentManager,
            TransactionConfirmationDialogFragment::class.java.simpleName)

    }

    override fun onConfirmBuyOrSellTransaction(amount: String) {

        pendingTransaction.amount = amount

        val currencyInfo = pendingTransaction.currencyInfo
        val operationType = pendingTransaction.operationType

        if(currencyInfo != null && operationType != null) {

            if(operationType == OperationType.SELL)
                viewModel.sell(currencyInfo.currency, amount)
            else
                viewModel.buy(currencyInfo.currency, amount)
        }
    }

    override fun onConfirmExchangeTransaction(toCurrency: String, amount: String) {

        pendingTransaction.amount = amount

        val fromCurrency = pendingTransaction.currencyInfo?.currency
        val operationType = pendingTransaction.operationType

        if(fromCurrency != null && operationType != null)
            viewModel.exchange(fromCurrency, toCurrency.toCurrencyEnum(), amount)
    }

    override fun onDestroy() {
        super.onDestroy()
        component = null
    }

    companion object {

        fun newInstance(): MarketFragment {
            return MarketFragment()
        }
    }
}
