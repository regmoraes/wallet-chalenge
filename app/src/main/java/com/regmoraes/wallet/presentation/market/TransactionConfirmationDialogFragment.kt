package com.regmoraes.wallet.presentation.market

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.regmoraes.wallet.R
import com.regmoraes.wallet.databinding.FragmentDialogTransactionBinding
import com.wallet.core.currency.data.Currency
import com.wallet.core.market.OperationType


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class TransactionConfirmationDialogFragment : DialogFragment() {

    private val currency by lazy { arguments?.get(ARG_CURRENCY) }
    private val operationType by lazy { arguments?.get(ARG_OPERATION_TYPE) }

    private lateinit var viewBinding: FragmentDialogTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentDialogTransactionBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = generateTitle()
        val hint = String.format(getString(R.string.transaction_amount_hint_format), operationType)

        viewBinding.textViewTitle.text = title
        viewBinding.editTextAmount.hint = hint
        viewBinding.buttonConfirm.setOnClickListener { onConfirmTransactionClicked() }
        viewBinding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun generateTitle(): String = when (operationType) {

        OperationType.BUY.name ->
            String.format(getString(R.string.transaction_buying_format), currency)

        OperationType.SELL.name ->
            String.format(getString(R.string.transaction_selling_format), currency)

        else -> {

            val currencyExchangingFor = when (currency) {

                Currency.BITCOIN.name -> Currency.BRITA.name
                else -> Currency.BITCOIN.name
            }

            String.format(
                getString(R.string.transaction_exchanging_format), currency,
                currencyExchangingFor
            )
        }
    }

    private fun onConfirmTransactionClicked() {

        val amount = viewBinding.editTextAmount.text.toString()

        if(amount.isBlank()) {
            viewBinding.editTextAmount.error =
                    getString(R.string.transaction_invalid_amount_message)
        } else {

            val listener = (targetFragment as OnDialogFragmentClicked)

            when(operationType) {

                OperationType.EXCHANGE.name ->
                    listener.onConfirmExchangeTransaction(getCurrencyExchangingFor(), amount)

                else -> listener.onConfirmBuyOrSellTransaction(amount)
            }

            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        val window = dialog.window
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

    interface OnDialogFragmentClicked {
        fun onConfirmBuyOrSellTransaction(amount: String)
        fun onConfirmExchangeTransaction(toCurrency: String, amount: String)
    }

    private fun getCurrencyExchangingFor(): String = when (currency) {

        Currency.BITCOIN -> Currency.BRITA.name
        else -> Currency.BITCOIN.name
    }

    companion object {

        const val ARG_OPERATION_TYPE = "operation-type"
        const val ARG_CURRENCY = "currency"

        fun newInstance(currency: String, operationType: String): TransactionConfirmationDialogFragment {
            val frag =
                TransactionConfirmationDialogFragment()
            val args = Bundle()
            args.putString(ARG_OPERATION_TYPE, operationType)
            args.putString(ARG_CURRENCY, currency)
            frag.arguments = args
            return frag
        }
    }
}