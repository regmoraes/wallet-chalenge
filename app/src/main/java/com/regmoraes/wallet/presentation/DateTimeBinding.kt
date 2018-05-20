package com.regmoraes.wallet.presentation

import android.databinding.BindingAdapter
import android.widget.TextView
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
object DateTimeBinding {

    private val outDateTimeFormatter: DateTimeFormatter
            = DateTimeFormat.forPattern("dd/MM/YYYY")

    @JvmStatic
    @BindingAdapter("databind:instant")
    fun bindDateTime(view: TextView, instant: Long) {

        val outLocalDateTime = LocalDateTime(instant).toString(outDateTimeFormatter)

        view.text = outLocalDateTime
    }
}
