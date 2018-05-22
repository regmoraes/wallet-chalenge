package com.regmoraes.wallet.presentation.transactions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.receipt.Receipt
import com.wallet.core.receipt.ReceiptManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
open class TransactionsHistoryViewModel(private val receiptManager: ReceiptManager) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val receiptsResource = MutableLiveData<Resource<List<Receipt>>>()

    init {
        getReceipts()
    }

    fun getReceipts() {

        disposables.add(
                receiptManager.getReceipts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { receiptsResource.postValue(Resource.loading()) }
                        .subscribe(
                                { receipts -> receiptsResource.postValue(Resource.success(receipts)) },
                                { error -> receiptsResource.postValue(Resource.error(error)) }
                        )
        )
    }

    open fun getReceiptsResource(): LiveData<Resource<List<Receipt>>> = receiptsResource

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
