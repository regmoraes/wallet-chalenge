package com.regmoraes.wallet.presentation.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.regmoraes.wallet.databinding.FragmentWalletBinding
import com.regmoraes.wallet.di.ComponentProvider
import com.regmoraes.wallet.di.component.ViewComponent
import com.regmoraes.wallet.presentation.Status
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class WalletFragment : Fragment() {

    private var component: ViewComponent? = null
    private lateinit var viewBinding: FragmentWalletBinding

    @Inject lateinit var viewModelFactory: WalletViewModelFactory
    private lateinit var viewModel: WalletViewModel

    private val walletsAdapter by lazy { WalletsAdapter() }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewBinding = FragmentWalletBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(viewBinding.recyclerViewWallets.context, layoutManager.orientation)

        viewBinding.recyclerViewWallets.layoutManager = layoutManager
        viewBinding.recyclerViewWallets.addItemDecoration(itemDecoration)
        viewBinding.recyclerViewWallets.adapter = walletsAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        component = (activity?.application as ComponentProvider).getViewComponent()
        component?.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WalletViewModel::class.java)

        viewModel.getWalletsResource().observe(this, Observer { resource ->

            if(resource != null && resource.status == Status.SUCCESS) {
                walletsAdapter.setData(resource.data)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        component = null
    }

    companion object {
        fun newInstance(): WalletFragment {
            return WalletFragment()
        }
    }
}
