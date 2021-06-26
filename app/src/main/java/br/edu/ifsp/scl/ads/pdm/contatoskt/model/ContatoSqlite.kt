package br.edu.ifsp.scl.ads.pdm.contatoskt.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.ads.pdm.contatoskt.R
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.CONTATO_TABLE
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.CREATE_CONTATO_TABLE_STATEMENT
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.EMAIL_COLUMN
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.FALSO_INTEIRO
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.LISTA_CONTATOS_DATABASE
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.NOME_COLUMN
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.SITE_COLUMN
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.TELEFONE_CELULAR_COLUMN
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.TELEFONE_COLUMN
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.TELEFONE_COMERCIAL_COLUMN
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.ContatoSqlite.Constantes.VERDADEIRO_INTEIRO
import java.sql.SQLException

class ContatoSqlite(contexto: Context): ContatoDao {

    object Constantes {
        val LISTA_CONTATOS_DATABASE = "listaContatos"
        val CONTATO_TABLE = "contato"
        val NOME_COLUMN = "nome"
        val EMAIL_COLUMN = "email"
        val TELEFONE_COLUMN = "telefone"
        val TELEFONE_COMERCIAL_COLUMN = "telefoneComercial"
        val TELEFONE_CELULAR_COLUMN = "telefoneCelular"
        val SITE_COLUMN = "site"

        val CREATE_CONTATO_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS ${CONTATO_TABLE} (" +
                "${NOME_COLUMN} TEXT NOT NULL PRIMARY KEY," +
                "${EMAIL_COLUMN} TEXT NOT NULL," +
                "${TELEFONE_COLUMN} TEXT NOT NULL," +
                "${TELEFONE_COMERCIAL_COLUMN} INT NOT NULL," +
                "${TELEFONE_CELULAR_COLUMN} TEXT NOT NULL," +
                "${SITE_COLUMN} TEXT NOT NULL );"

        val FALSO_INTEIRO = 0
        val VERDADEIRO_INTEIRO = 1
    }


    val listaContatosDb: SQLiteDatabase
    init {
        listaContatosDb = contexto.openOrCreateDatabase(
            LISTA_CONTATOS_DATABASE,
            MODE_PRIVATE,
            null
        )
        try {
            listaContatosDb.execSQL(CREATE_CONTATO_TABLE_STATEMENT)
        }
        catch (se: SQLException) {
            Log.e(contexto.getString(R.string.app_name), se.toString())
        }
    }

    private fun contentValuesFromContato(contato: Contato, includePrimaryKey: Boolean): ContentValues {
        val valores = ContentValues()
        with(contato) {
            if (includePrimaryKey) {
                valores.put(NOME_COLUMN, nome)
            }
            valores.put(EMAIL_COLUMN, email)
            valores.put(TELEFONE_COLUMN, telefone)
            valores.put(TELEFONE_COMERCIAL_COLUMN, if (telefoneComercial) VERDADEIRO_INTEIRO else FALSO_INTEIRO)
            valores.put(TELEFONE_CELULAR_COLUMN, telefoneCelular)
            valores.put(SITE_COLUMN, site)
        }
        return valores
    }

    override fun createContato(contato: Contato) {
        listaContatosDb.insert(CONTATO_TABLE, null,
            contentValuesFromContato(contato, true))
    }

    override fun readContato(nome: String): Contato {
        val contatoCursor = listaContatosDb.query(
            true,
            CONTATO_TABLE,
            null,
            "${NOME_COLUMN} = ?",
            arrayOf(nome),
            null,
            null,
            null,
            null
        )

        return if (contatoCursor.moveToFirst()) {
            contatoFromCursor(contatoCursor)
        }
        else {
            Contato()
        }
    }

    private fun contatoFromCursor(contatoCursor: Cursor): Contato = with (contatoCursor) {
        Contato(
            getString(getColumnIndex(NOME_COLUMN)),
            getString(getColumnIndex(EMAIL_COLUMN)),
            getString(getColumnIndex(TELEFONE_COLUMN)),
            getInt(getColumnIndex(TELEFONE_COMERCIAL_COLUMN)) != FALSO_INTEIRO,
            getString(getColumnIndex(TELEFONE_CELULAR_COLUMN)),
            getString(getColumnIndex(SITE_COLUMN)),
        )
    }

    override fun readContatos(): MutableList<Contato> {
        val contatosList: MutableList<Contato> = mutableListOf()

        val consultaQuery = "SELECT * FROM ${CONTATO_TABLE}"
        val contatosCursor = listaContatosDb.rawQuery(consultaQuery, null)
        while (contatosCursor.moveToNext()) {
            contatosList.add(contatoFromCursor(contatosCursor))
        }
        return contatosList
    }

    override fun updateContato(contato: Contato) {
        listaContatosDb.update(
            CONTATO_TABLE,
            contentValuesFromContato(contato, false),
            "${NOME_COLUMN} = ?",
            arrayOf(contato.nome)
        )
    }

    override fun deleteContato(nome: String) {
        listaContatosDb.delete(
            CONTATO_TABLE,
            "${NOME_COLUMN} = ?",
            arrayOf(nome)
        )
    }
}