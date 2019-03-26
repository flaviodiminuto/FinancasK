package com.flavio.android.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.formataParaMoedaBrasileira() : String{
    val moedaBrasileira = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return moedaBrasileira.format(this)
            .replace("R$","R$ ")
            .replace("-R$", "R$ -")
}