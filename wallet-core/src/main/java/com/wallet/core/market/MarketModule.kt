package com.wallet.core.market

import com.wallet.core.receipt.ReceiptManager
import com.wallet.core.wallet.WalletManager
import dagger.Module
import dagger.Provides

/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
@Module
class MarketModule {

    @Provides
    fun providesExchangeCalculator(): ExchangeCalculator {

        return ExchangeCalculator()
    }

    @Provides
    fun providesMarketManager(walletManager: WalletManager,
                              receiptManager: ReceiptManager,
                              exchangeCalculator: ExchangeCalculator): MarketManager {

        return MarketManager(walletManager, receiptManager, exchangeCalculator)
    }
}