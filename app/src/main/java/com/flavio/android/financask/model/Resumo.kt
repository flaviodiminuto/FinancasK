package com.flavio.android.financask.model

import java.math.BigDecimal

class Resumo (private val transacoes : List<Transacao>){

    val receita  get() = somaPor(TipoTransacao.RECEITA)

    val despesa get() = somaPor(TipoTransacao.DESPESA)

    val total get() = receita.subtract(despesa)

    fun somaPor(tipo : TipoTransacao): BigDecimal{
       return BigDecimal(transacoes
                .filter{it.tipoTransacao == tipo}
                .sumByDouble { it.valor.toDouble()})
    }


}