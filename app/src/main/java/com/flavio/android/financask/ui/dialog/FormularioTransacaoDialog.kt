package com.flavio.android.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.flavio.android.financask.R
import com.flavio.android.financask.extension.converteParaCalendar
import com.flavio.android.financask.extension.formataParaBrasileiro
import com.flavio.android.financask.model.TipoTransacao
import com.flavio.android.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    protected abstract val tituloBotaoPositivo : String

    fun chama(tipo : TipoTransacao, delegate:(transacao: Transacao)-> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo : TipoTransacao, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloBotaoPositivo) { _, _ ->
                    val dataTexto = campoData.text.toString()
                    val categoriaTexto = campoCategoria.selectedItem.toString()
                    val valorTransacao = convertCampoValor()
                    val data = dataTexto.converteParaCalendar()
                    val transacaoCriada = Transacao(valorTransacao,
                            categoriaTexto,
                            tipo,
                            data)

                    delegate(transacaoCriada)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    protected abstract fun tituloPor(tipo: TipoTransacao): Int

    private fun convertCampoValor(): BigDecimal {
        return try {
            BigDecimal(campoValor.text.toString())
        } catch (nbe: NumberFormatException) {
            Toast.makeText(context, "Valor inválido", Toast.LENGTH_LONG).show()
            BigDecimal(0)
        }
    }

    private fun configuraCampoCategoria(tipo: TipoTransacao) {
        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(context,
                categorias,
                R.layout.support_simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    protected fun categoriaPor(tipo: TipoTransacao): Int {
        return if (tipo == TipoTransacao.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)


        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)
                        campoData.setText(dataSelecionada.formataParaBrasileiro())
                    }, ano, mes, dia).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
    }
}