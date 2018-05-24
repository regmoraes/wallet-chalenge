package com.regmoraes.wallet.presentation.wallet

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.wallet.data.Wallet
import com.wallet.core.wallet.domain.WalletManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
open class WalletViewModel(private val walletManager: WalletManager) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val walletsResource = MutableLiveData<Resource<List<Wallet>>>()

    init {
        getWallets()
    }

    private fun getWallets() {

        disposables.add(
            walletManager.getWallets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { walletsResource.setValue(Resource.loading()) }
                .subscribe(
                    { wallets -> walletsResource.setValue(Resource.success(wallets)) },
                    { error -> walletsResource.setValue(Resource.error(error)) }
                )
        )
    }

    open fun getWalletsResource(): LiveData<Resource<List<Wallet>>> = walletsResource

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
