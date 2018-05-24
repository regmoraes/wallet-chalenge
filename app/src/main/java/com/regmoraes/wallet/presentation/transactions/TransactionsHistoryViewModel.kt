package com.regmoraes.wallet.presentation.transactions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.transaction.data.Transaction
import com.wallet.core.transaction.domain.TransactionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
open class TransactionsHistoryViewModel(private val transactionManager: TransactionManager) :
    ViewModel() {

    private val disposables = CompositeDisposable()

    private val transactionsResource = MutableLiveData<Resource<List<Transaction>>>()

    init {
        getTransactions()
    }

    fun getTransactions() {

        disposables.add(
            transactionManager.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { transactionsResource.postValue(Resource.loading()) }
                .subscribe(
                    { transactions -> transactionsResource.postValue(Resource.success(transactions)) },
                    { error -> transactionsResource.postValue(Resource.error(error)) }
                )
        )
    }

    open fun getTransactionsResource(): LiveData<Resource<List<Transaction>>> = transactionsResource

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
