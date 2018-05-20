package com.wallet.core.currency

import com.wallet.api.CurrencyApi
import com.wallet.api.bitcoin.BitcoinApi
import com.wallet.api.brita.BritaApi
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.CurrencyApiManager
import com.wallet.core.currency.data.CurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
@Module
class CurrencyModule {

    @Provides
    fun providesBitcoinApi(): BitcoinApi {

        return BitcoinApi()
    }

    @Provides
    fun providesBritaApi(): BritaApi {

        return BritaApi()
    }

    @Provides
    fun providesCurrencyRepository(bitcoinApi: BitcoinApi, britaApi: BritaApi): CurrencyRepository.Remote {

        val apis: HashMap<Currency, CurrencyApi> =
            hashMapOf(Currency.BITCOIN to bitcoinApi, Currency.BRITA to britaApi)

        return CurrencyApiManager(apis)
    }

    @Provides
    fun providesCurrencyManager(repository: CurrencyRepository.Remote): CurrencyManager {

        return CurrencyManager(repository)
    }
}