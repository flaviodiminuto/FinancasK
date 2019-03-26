package com.flavio.android.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.flavio.android.financask.R
import com.flavio.android.financask.extension.formataParaMoedaBrasileira
import com.flavio.android.financask.model.Resumo
import com.flavio.android.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView (private val view : View,
                  transacoes : List<Transacao>,
                  context: Context){

    private val resumo : Resumo  = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.colorPrimary)
    private val corDespesa = ContextCompat.getColor(context, R.color.colorAccent)

    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }
    private fun adicionaReceita() {
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = resumo.receita.formataParaMoedaBrasileira()
        }
    }

    private fun adicionaDespesa() {
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = resumo.despesa.formataParaMoedaBrasileira()
        }
    }

    private fun adicionaTotal(){
        val total = resumo.total
            with(view.resumo_card_total) {
                setTextColor(corPor(total))
                text = total.formataParaMoedaBrasileira()
            }
    }

    private fun corPor(valor: BigDecimal): Int {
        return  if (valor < BigDecimal.ZERO)
            corDespesa
        else
            corReceita
    }

}