package com.flavio.android.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import com.flavio.android.financask.R
import com.flavio.android.financask.dao.TransacaoDAO
import com.flavio.android.financask.model.TipoTransacao
import com.flavio.android.financask.model.Transacao
import com.flavio.android.financask.ui.ResumoView
import com.flavio.android.financask.ui.adapter.ListaTransacoesAdapter
import com.flavio.android.financask.ui.dialog.AdicionaTransacaoDialog
import com.flavio.android.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTrasacoesActivity : AppCompatActivity() {
    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes
    private val viewDaActivity by lazy {
        window.decorView as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(TipoTransacao.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(TipoTransacao.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: TipoTransacao) {
        AdicionaTransacaoDialog(viewDaActivity , this)
                .chama(tipo) {transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }

    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(viewDaActivity, transacoes, this)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogDeAlteracao(transacao, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE,1,Menu.NONE,"Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if(idDoMenu == 1){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            removeTransacao(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun removeTransacao(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(viewDaActivity , this)
            .chama(transacao){ transacaoAlterada ->
                    altera(transacaoAlterada, position)
            }
    }

    private fun altera(transacao: Transacao, position: Int) {
        dao.altera(transacao, position)
        atualizaTransacoes()
    }

}