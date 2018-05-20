package com.regmoraes.wallet.presentation.wallet

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.wallet.WalletManager
import com.wallet.core.wallet.data.Wallet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class WalletViewModel(private val walletManager: WalletManager) : ViewModel() {

    private val disposables = CompositeDisposable()

    val walletsResource = MutableLiveData<Resource<List<Wallet>>>()

    fun getWallets() {

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

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
