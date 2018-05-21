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
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class MarketFragment : Fragment() {

    private var component: ViewComponent? = null
    private lateinit var viewBinding: FragmentMarketBinding

    @Inject lateinit var viewModelFactory: MarketViewModelFactory
    lateinit var viewModel: MarketViewModel
    var adapter = MarketCurrencyInfoAdapter()

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

//    override fun onResume() {
//        super.onResume()
//
//        viewBinding.buttonBuy.setOnClickListener {
//
//            val amount = viewBinding.editTextAmount.text.toString()
//
//            //viewModel.buy(Currency.BITCOIN, amount)
//        }
//
//        viewBinding.buttonSell.setOnClickListener {
//
//            val amount = viewBinding.editTextAmount.text.toString()
//
//            //viewModel.sell(Currency.BITCOIN, amount)
//        }
//    }

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
