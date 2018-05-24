package com.regmoraes.wallet.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.wallet.core.currency.data.Currency
import com.wallet.core.currency.data.toCurrencyEnum


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object CurrencyConverter {

    @JvmStatic
    @TypeConverter
    fun toCurrency(string: String): Currency {
        return string.toCurrencyEnum()
    }

    @JvmStatic
    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return currency.name
    }
}