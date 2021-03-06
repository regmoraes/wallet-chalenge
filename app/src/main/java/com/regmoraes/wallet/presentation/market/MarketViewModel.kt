package com.regmoraes.wallet.presentation.market

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.regmoraes.wallet.presentation.Resource
import com.regmoraes.wallet.presentation.SingleLiveEvent
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyInfo
import com.wallet.core.currency.domain.CurrencyManager
import com.wallet.core.market.domain.MarketManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
open class MarketViewModel(
    private val marketManager: MarketManager,
    private val currencyManager: CurrencyManager
) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }
    private val currenciesInfoResource by lazy { MutableLiveData<Resource<List<CurrencyInfo>>>() }
    private val transactionFinishedEvent by lazy { SingleLiveEvent<Resource<Boolean>>() }
    private val currenciesInfo by lazy { mutableListOf<CurrencyInfo>() }

    open fun getAllCurrenciesTodayPrice() {

        currenciesInfo.clear()

        val todayInstant = Calendar.getInstance().timeInMillis

        disposables.add(
            currencyManager.getAllCurrenciesInfo(todayInstant)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { currenciesInfoResource.postValue(Resource.loading()) }
                .subscribe(
                    { currencyInfo ->
                        currenciesInfo.add(currencyInfo)
                        currenciesInfoResource.postValue(Resource.success(currenciesInfo))
                    },
                    { error -> Timber.d(error) }
                )
        )
    }

    fun buy(currency: Currency, amount: String) {

        val currencyInfo = currenciesInfo.find { it.currency == currency }

        if (currencyInfo != null) {

            disposables.add(
                marketManager.buy(currencyInfo, amount.toBigDecimal())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { transactionFinishedEvent.postValue(Resource.success(null)) },
                        { error -> transactionFinishedEvent.postValue(Resource.error(error)) }
                    )
            )
        }
    }

    fun sell(currency: Currency, amount: String) {

        val currencyInfo = currenciesInfo.find { it.currency == currency }

        if (currencyInfo != null) {

            disposables.add(
                marketManager.sell(currencyInfo, amount.toBigDecimal())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { transactionFinishedEvent.postValue(Resource.success(null)) },
                        { error -> transactionFinishedEvent.postValue(Resource.error(error)) }
                    )
            )
        }
    }

    fun exchange(fromCurrency: Currency, toCurrency: Currency, amount: String) {

        val fromCurrencyInfo = currenciesInfo.find { it.currency == fromCurrency }
        val toCurrencyInfo = currenciesInfo.find { it.currency == toCurrency }

        if (fromCurrencyInfo != null && toCurrencyInfo != null) {

            marketManager.exchange(fromCurrencyInfo, toCurrencyInfo, amount.toBigDecimal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { transactionFinishedEvent.postValue(Resource.success(null)) },
                    { error -> transactionFinishedEvent.postValue(Resource.error(error)) }
                )
        }
    }

    open fun getCurrencyInfoResource(): LiveData<Resource<List<CurrencyInfo>>> =
        currenciesInfoResource

    open fun getTransactionFinishedEvent(): LiveData<Resource<Boolean>> = transactionFinishedEvent


    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }
}