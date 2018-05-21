package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.regmoraes.wallet.R
import com.regmoraes.wallet.WalletApp
import com.regmoraes.wallet.databinding.FragmentMarketBinding
import com.regmoraes.wallet.di.component.ViewComponent
import com.regmoraes.wallet.presentation.Status
import com.wallet.core.currency.data.Currency
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class MarketFragment : Fragment() {

    private var component: ViewComponent? = null
    private lateinit var viewBinding: FragmentMarketBinding

    @Inject lateinit var viewModelFactory: MarketViewModelFactory
    lateinit var viewModel: MarketViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewBinding = FragmentMarketBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        component = (activity?.application as WalletApp).appComponent.marketComponent()
        component?.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MarketViewModel::class.java)

        viewModel.walletTotalAmountResource.observe(this, Observer { resource ->

            if(resource != null) {

                when(resource.status) {

                    Status.SUCCESS -> {
                        viewBinding.textViewWalletTotalAmount.text =
                                String.format(getString(R.string.amount), resource.data.toString())
                    }

                    Status.ERROR -> {
                        Toast.makeText(context, resource.error?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {}
                }
            }
        })

        viewModel.getWalletTotalAmount()
        viewModel.getAllCurrenciesTodayPrice()

    }

    override fun onResume() {
        super.onResume()

        viewBinding.buttonBuy.setOnClickListener {

            val amount = viewBinding.editTextAmount.text.toString()

            //viewModel.buy(Currency.BITCOIN, amount)
        }

        viewBinding.buttonSell.setOnClickListener {

            val amount = viewBinding.editTextAmount.text.toString()

            //viewModel.sell(Currency.BITCOIN, amount)
        }
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
