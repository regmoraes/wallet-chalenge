package com.wallet.core.market

import com.wallet.core.market.domain.ExchangeCalculator
import com.wallet.core.market.domain.MarketManager
import com.wallet.core.receipt.domain.ReceiptManager
import com.wallet.core.wallet.domain.WalletManager
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