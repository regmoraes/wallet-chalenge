package com.wallet.api.brita.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class Value(

		@Expose(serialize = false)
		@field:SerializedName("cotacaoCompra")
		val cotacaoCompra: Double,

		@Expose(serialize = false)
		@field:SerializedName("cotacaoVenda")
		val cotacaoVenda: Double,

		@Expose(serialize = false)
		@field:SerializedName("tipoBoletim")
		val tipoBoletim: String,

		@Expose(serialize = false)
		@field:SerializedName("dataHoraCotacao")
		val dataHoraCotacao: String,

		@Expose(serialize = false)
		@field:SerializedName("paridadeCompra")
		val paridadeCompra: Double,

		@Expose(serialize = false)
		@field:SerializedName("paridadeVenda")
		val paridadeVenda: Double
)