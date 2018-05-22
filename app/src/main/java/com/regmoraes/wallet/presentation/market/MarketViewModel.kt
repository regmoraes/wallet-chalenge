package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.wallet.core.currency.CurrencyManager
import com.wallet.core.currency.data.Currency
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
open class MarketViewModel(private val marketManager: MarketManager,
                           private val currencyManager: CurrencyManager,
                           private val walletManager: WalletManager) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val walletBaseCurrencyAmountResource = MutableLiveData<Resource<BigDecimal>>()
    private val currenciesInfoResource = MutableLiveData<Resource<List<CurrencyInfo>>>()

    val currenciesInfo = mutableListOf<CurrencyInfo>()

    init {

        getAllCurrenciesTodayPrice()
        getWalletTotalAmount()
    }

    private fun getAllCurrenciesTodayPrice() {

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

    fun buy(currency: Currency, amount: String) {

        val currencyInfo = currenciesInfo.find { it.currency == currency }

        if(currencyInfo != null) {

            disposables.add(
                marketManager.buy(currencyInfo, amount.toBigDecimal())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { getWalletTotalAmount() },
                        { error -> walletBaseCurrencyAmountResource.postValue(Resource.error(error)) }
                    )
            )
        }
    }

    fun sell(currency: Currency, amount: String) {

        val currencyInfo = currenciesInfo.find { it.currency == currency }

        if(currencyInfo != null) {

            disposables.add(
                marketManager.sell(currencyInfo, amount.toBigDecimal())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { getWalletTotalAmount() },
                        { error -> walletBaseCurrencyAmountResource.postValue(Resource.error(error)) }
                    )
            )
        }
    }

    fun exchange(fromCurrency: Currency, toCurrency: Currency, amount: String) {

        val fromCurrencyInfo = currenciesInfo.find { it.currency == fromCurrency }
        val toCurrencyInfo = currenciesInfo.find { it.currency == toCurrency }

        if(fromCurrencyInfo != null && toCurrencyInfo != null) {

            disposables.add(
                marketManager.exchange(fromCurrencyInfo, toCurrencyInfo, amount.toBigDecimal())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { getWalletTotalAmount() },
                        { error -> walletBaseCurrencyAmountResource.postValue(Resource.error(error)) }
                    )
            )
        }
    }

    open fun getCurrencyInfoResource() : LiveData<Resource<List<CurrencyInfo>>> = currenciesInfoResource
    open fun getWalletBaseCurrencyAmountResource() : LiveData<Resource<BigDecimal>> = walletBaseCurrencyAmountResource

    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }
}