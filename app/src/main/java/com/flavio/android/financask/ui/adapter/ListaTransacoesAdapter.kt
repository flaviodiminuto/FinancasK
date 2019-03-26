package com.flavio.android.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.flavio.android.financask.R
import com.flavio.android.financask.extension.formataParaBrasileiro
import com.flavio.android.financask.extension.formataParaMoedaBrasileira
import com.flavio.android.financask.extension.limitaEmAte
import com.flavio.android.financask.model.TipoTransacao
import com.flavio.android.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val transacao = transacoes[position]

        adicionaValor(transacao, inflate)
        adicionaIcone(transacao, inflate)
        adicionaCategoria(inflate, transacao)
        adicionaData(inflate, transacao)
        return inflate
    }

    private fun adicionaData(inflate: View, transacao: Transacao) {
        inflate.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(inflate: View, transacao: Transacao) {
        inflate.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(transacao: Transacao, inflate: View) {
        val icone : Int = iconePor(transacao.tipoTransacao)
        inflate.transacao_icone.setBackgroundResource(icone)
    }

    private fun iconePor(tipo : TipoTransacao): Int {
        return if (tipo == TipoTransacao.RECEITA) {
            R.drawable.icone_transacao_item_receita
        } else {
            R.drawable.icone_transacao_item_despesa
        }
    }

    private fun adicionaValor(transacao: Transacao, inflate: View) {
        val cor: Int = corPor(transacao.tipoTransacao)
        inflate.transacao_valor.setTextColor(cor)
        inflate.transacao_valor.text = transacao.valor.formataParaMoedaBrasileira()
    }

    private fun corPor(tipo : TipoTransacao): Int {
        return if (tipo== TipoTransacao.RECEITA) {
            ContextCompat.getColor(context, R.color.bright_foreground_material_light)
        } else {
            ContextCompat.getColor(context, R.color.colorAccent)
        }
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }



}