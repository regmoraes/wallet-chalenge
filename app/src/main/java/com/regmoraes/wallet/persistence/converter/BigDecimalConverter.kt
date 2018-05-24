package com.regmoraes.wallet.persistence.converter

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal


/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/

object BigDecimalConverter {

    @JvmStatic
    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal): String {
        return bigDecimal.toEngineeringString()
    }

    @JvmStatic
    @TypeConverter
    fun toBigDecimal(string: String): BigDecimal {
        return BigDecimal(string)
    }
}