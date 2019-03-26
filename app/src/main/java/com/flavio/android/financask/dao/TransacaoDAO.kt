package com.flavio.android.financask.dao

import com.flavio.android.financask.model.Transacao

class TransacaoDAO {
    val transacoes : List<Transacao> = Companion.transacoes
    companion object {
        private val transacoes : MutableList<Transacao> = mutableListOf()
    }
constructor()
    fun adiciona(transacao : Transacao){
        Companion.transacoes.add(transacao)
    }

    fun altera(transacao: Transacao, posicao : Int){
        Companion.transacoes[posicao] = transacao
    }

    fun remove(posicao : Int){
        Companion.transacoes.removeAt(posicao)
    }
    fun remove(transacao: Transacao){
        Companion.transacoes.remove(transacao)
    }

}