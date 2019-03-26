package com.flavio.android.financask.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Calendar

@Entity
class Transacao{
    @PrimaryKey(autoGenerate = true)
    var idTransacao : Int = 0
    var valor  = BigDecimal.ZERO
    var categoria : String = "indefinida"
    var tipoTransacao = TipoTransacao.RECEITA
    var data = Calendar.getInstance()

    constructor()
}
