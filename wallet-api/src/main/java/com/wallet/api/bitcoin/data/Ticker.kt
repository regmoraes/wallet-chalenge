package com.wallet.api.bitcoin.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class Ticker(

        @Expose(serialize = false)
        @field:SerializedName("date")
        val date: Long,

        @Expose(serialize = false)
        @field:SerializedName("last")
        val last: String
)