package com.wallet.api

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
fun Long.toDateTimeString(outputFormat: String): String {

    return DateTime(this).toString(outputFormat)
}

fun String.asDateTimeToLong(inputFormat: String): Long {

    val formatter = DateTimeFormat.forPattern(inputFormat)

    val dateTime = DateTime.parse(this, formatter).minusDays(1)

    return dateTime.millis
}