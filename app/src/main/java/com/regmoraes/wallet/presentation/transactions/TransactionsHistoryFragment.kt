package com.regmoraes.wallet.presentation.transactions

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
import com.regmoraes.wallet.databinding.FragmentTransactionsHistoryBinding
import com.regmoraes.wallet.di.ComponentProvider
import com.regmoraes.wallet.di.component.ViewComponent
import com.regmoraes.wallet.presentation.Status
import com.wallet.core.receipt.Receipt
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionsHistoryFragment : Fragment() {

    private var component: ViewComponent? = null
    private lateinit var viewBinding: FragmentTransactionsHistoryBinding

    @Inject lateinit var viewModelFactory: TransactionsHistoryViewModelFactory
    private lateinit var viewModel: TransactionsHistoryViewModel

    private val transactionsHistoryAdapter by lazy { TransactionsHistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewBinding = FragmentTransactionsHistoryBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(viewBinding.recyclerViewReceipts.context, layoutManager.orientation)

        viewBinding.recyclerViewReceipts.layoutManager = layoutManager
        viewBinding.recyclerViewReceipts.addItemDecoration(itemDecoration)
        viewBinding.recyclerViewReceipts.adapter = transactionsHistoryAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        component = (activity?.application as ComponentProvider).getViewComponent()
        component?.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TransactionsHistoryViewModel::class.java)

        viewModel.getReceiptsResource().observe(this, Observer { resource ->

            if(resource != null) {

                when(resource.status) {

                    Status.LOADING -> {}
                    Status.SUCCESS -> {

                        if(resource.data == null || resource.data.isEmpty())
                            showEmptyReceiptsMessage()
                        else
                            showReceipts(resource.data)
                    }
                    Status.ERROR -> showEmptyReceiptsMessage() }
            }
        })
    }

    private fun showReceipts(receipt: List<Receipt>?) {
        viewBinding.textViewReceiptsErrorMessage.visibility = View.GONE
        viewBinding.recyclerViewReceipts.visibility = View.VISIBLE

        transactionsHistoryAdapter.setData(receipt)
    }

    private  fun showEmptyReceiptsMessage() {
        viewBinding.textViewReceiptsErrorMessage.visibility = View.VISIBLE
        viewBinding.recyclerViewReceipts.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        component = null
    }

    companion object {
        fun newInstance(): TransactionsHistoryFragment {
            return TransactionsHistoryFragment()
        }
    }
}
