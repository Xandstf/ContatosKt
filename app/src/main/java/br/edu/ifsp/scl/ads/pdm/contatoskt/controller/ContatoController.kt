package br.edu.ifsp.scl.ads.pdm.contatoskt.controller

import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Contato
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoDao
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoFirebase
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite
import br.edu.ifsp.scl.ads.pdm.contatoskt.view.MainActivity

class ContatoController(mainActivity: MainActivity) {
    val contatoDao: ContatoDao
    init {
        //contatoDao = ContatoSqlite(mainActivity)
        contatoDao = ContatoFirebase()
    }

    fun insereContato(contato: Contato) = contatoDao.createContato(contato)
    fun buscaContato(nome: String) = contatoDao.readContato(nome)
    fun buscaContatos() = contatoDao.readContatos()
    fun atualizaContato(contato: Contato) = contatoDao.updateContato(contato)
    fun removeContato(nome: String) = contatoDao.deleteContato(nome)
}