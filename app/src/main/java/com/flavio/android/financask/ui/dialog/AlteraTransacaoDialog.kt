package com.flavio.android.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.flavio.android.financask.R
import com.flavio.android.financask.extension.formataParaBrasileiro
import com.flavio.android.financask.model.TipoTransacao
import com.flavio.android.financask.model.Transacao

class AlteraTransacaoDialog(viewGroup: ViewGroup,
                            private val context: Context) : FormularioTransacaoDialog(context,viewGroup) {
    override val tituloBotaoPositivo: String
        get() = "Alterar"


    fun chama(transacao:Transacao, delegate: (transacao : Transacao) -> Unit) {
        val tipo = transacao.tipoTransacao
        super.chama(tipo, delegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(transacao: Transacao) {
        val tipo = transacao.tipoTransacao
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(String.format("%f",transacao.valor))
    }

    override fun tituloPor(tipo: TipoTransacao): Int {
        return if (tipo == TipoTransacao.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }
}