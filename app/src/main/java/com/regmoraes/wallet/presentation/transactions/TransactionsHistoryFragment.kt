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
import com.regmoraes.wallet.WalletApp
import com.regmoraes.wallet.databinding.FragmentTransactionsHistoryBinding
import com.regmoraes.wallet.di.component.ViewComponent
import com.regmoraes.wallet.presentation.Status
import com.wallet.core.receipt.Receipt
import kotlinx.android.synthetic.main.fragment_transactions_history.view.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionsHistoryFragment : Fragment(), TransactionHistoryContract.View {

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
            DividerItemDecoration(viewBinding.root.recyclerView_receipts.context, layoutManager.orientation)

        viewBinding.root.recyclerView_receipts.layoutManager = layoutManager
        viewBinding.root.recyclerView_receipts.addItemDecoration(itemDecoration)
        viewBinding.root.recyclerView_receipts.adapter = transactionsHistoryAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        component = (activity?.application as WalletApp).appComponent.marketComponent()
        component?.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionsHistoryViewModel::class.java)

        viewModel.receiptsResource.observe(this, Observer { resource ->

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

        viewModel.getReceipts()

    }

    override fun showReceipts(receipt: List<Receipt>?) {
        viewBinding.textViewReceiptsErrorMessage.visibility = View.GONE
        viewBinding.recyclerViewReceipts.visibility = View.VISIBLE

        transactionsHistoryAdapter.setData(receipt)
    }

    override fun showEmptyReceiptsMessage() {
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
