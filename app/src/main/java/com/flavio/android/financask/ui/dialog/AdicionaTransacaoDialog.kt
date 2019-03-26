package com.flavio.android.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.flavio.android.financask.R
import com.flavio.android.financask.model.TipoTransacao

class AdicionaTransacaoDialog(viewGroup: ViewGroup,
                              context: Context) : FormularioTransacaoDialog(context, viewGroup) {
    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: TipoTransacao): Int {
        return if (tipo == TipoTransacao.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }
}