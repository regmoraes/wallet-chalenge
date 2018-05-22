package com.wallet.core.currency

import com.wallet.core.currency.data.Currency

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
fun String.toCurrencyEnum(): Currency {

    return when (this) {

        Currency.BRL.name -> Currency.BRL
        Currency.BITCOIN.name -> Currency.BITCOIN
        Currency.BRITA.name -> Currency.BRITA
        else -> throw IllegalArgumentException()
    }
}
