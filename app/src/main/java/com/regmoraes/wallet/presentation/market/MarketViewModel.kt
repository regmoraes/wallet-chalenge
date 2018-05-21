package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.currency.CurrencyManager
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.market.MarketManager
import com.wallet.core.wallet.WalletManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.math.BigDecimal
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class MarketViewModel(private val marketManager: MarketManager,
                      private val currencyManager: CurrencyManager,
                      private val walletManager: WalletManager) : ViewModel() {

    private val disposables = CompositeDisposable()

    val walletBaseCurrencyAmountResource = MutableLiveData<Resource<BigDecimal>>()
    val britaInfoResource = MutableLiveData<Resource<CurrencyInfo>>()
    val bitcoinInfoResource = MutableLiveData<Resource<CurrencyInfo>>()
    val currenciesInfoResource = MutableLiveData<Resource<List<CurrencyInfo>>>()
    val currenciesInfo = mutableListOf<CurrencyInfo>()

    init {

        getAllCurrenciesTodayPrice()
        getWalletTotalAmount()
    }

    private fun getWalletTotalAmount() {

        disposables.add(
                walletManager.getBaseWallet()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { baseWallet ->
                                    walletBaseCurrencyAmountResource
                                        .postValue(Resource.success(baseWallet.amount)) },
                                { error ->
                                    walletBaseCurrencyAmountResource.postValue(Resource.error(error))
                                }
                        )
        )
    }

//    fun buy(currency: Currency, amount: String) {
//
//        val buyOperation: Single<Receipt> = when(currency) {
//
//            Currency.BTC -> marketManager.buy(bitcoinInfoResource.value?.data!!,
//                    amount.toBigDecimal())
//
//            else -> marketManager.buy(britaInfoResource.value?.data!!,
//                    amount.toBigDecimal())
//        }
//
//        disposables.add(
//                buyOperation
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                { getWalletTotalAmount() },
//                                { error -> walletBaseCurrencyAmountResource.postValue(Resource.error(error)) }
//                        )
//        )
//    }

//    fun sell(currency: Currency, amount: String) {
//
//        val sellOperation: Single<Receipt> = when(currency) {
//
//            Currency.BTC -> marketManager.sell(bitcoinInfoResource.value?.data!!,
//                    amount.toBigDecimal())
//
//            else -> marketManager.sell(britaInfoResource.value?.data!!,
//                    amount.toBigDecimal())
//        }
//
//        disposables.add(
//                sellOperation
//                        .flatMap { walletManager.getBaseWallet() }
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                { baseWallet ->
//                                    walletBaseCurrencyAmountResource
//                                        .postValue(Resource.success(baseWallet.amount)) },
//                                { error ->
//                                    walletBaseCurrencyAmountResource.postValue(Resource.error(error))
//                                }
//                        )
//        )
//    }

    fun getAllCurrenciesTodayPrice() {

        val todayInstant = Calendar.getInstance().timeInMillis

        disposables.add(
                    currencyManager.getAllCurrenciesInfo(todayInstant)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { currencyInfo ->

                                        currenciesInfo.add(currencyInfo)

                                        currenciesInfoResource.postValue(Resource.success(currenciesInfo))
                                    },
                                    { error -> Timber.d(error) }
                            )
            )
    }

    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }
}