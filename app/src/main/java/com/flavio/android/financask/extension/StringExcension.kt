package com.flavio.android.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String{
    if(this.length>caracteres){
        val primeiroCaracter = 0 //Ctrl+Alt+v  sobre um valor cria uma variavel
        return "${this.substring(primeiroCaracter,caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar(): Calendar {
    val dataFormato = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = dataFormato.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}