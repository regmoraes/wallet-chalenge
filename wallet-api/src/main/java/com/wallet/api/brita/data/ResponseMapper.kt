package com.wallet.api.brita.data

import com.wallet.api.CurrencyInfo
import com.wallet.api.asDateTimeToLong
import com.wallet.api.brita.BritaApi
import java.lang.IllegalStateException
import java.math.BigDecimal

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
fun InfoResponse.toCurrencyInfo() : CurrencyInfo {

    val tipoBoletim = "Fechamento PTAX"

    val value = values.find { it.tipoBoletim == tipoBoletim }

    return if (value != null) {

        val price = BigDecimal.valueOf(value.cotacaoCompra)
        val date = value.dataHoraCotacao.asDateTimeToLong(BritaApi.DATE_TIME_RESPONSE_PATTERN)

        CurrencyInfo(price, date)

    } else {
        throw IllegalStateException("No value available in response")
    }

}