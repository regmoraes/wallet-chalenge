package com.wallet.api.bitcoin.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class InfoResponse(

		@Expose(serialize = false)
		@field:SerializedName("ticker")
		val ticker: Ticker
)