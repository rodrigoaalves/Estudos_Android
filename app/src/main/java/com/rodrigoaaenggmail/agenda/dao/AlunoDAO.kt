package com.rodrigoaaenggmail.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rodrigoaaenggmail.agenda.modelo.Aluno

/* Versão 5 da tabela da dados */
class AlunoDAO(context: Context) : SQLiteOpenHelper(context, "Agenda", null, 5) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql =
            "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT,  comentario TEXT,  nota REAL)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Alunos"
        db.execSQL(sql)
        onCreate(db)
    }

    fun insere (aluno: Aluno){
        val db = writableDatabase
        val dados = pegaDadosDoAluno(aluno)

        db.insert("Alunos", null, dados)
    }

    private fun pegaDadosDoAluno(aluno: Aluno): ContentValues {
        val dados = ContentValues()
        dados.put("nome", aluno.nome)
        dados.put("endereco", aluno.endereco)
        dados.put("site", aluno.site)
        dados.put("telefone", aluno.telefone)
        dados.put("comentario", aluno.comentario)
        dados.put("nota", aluno.nota)
        return dados
    }

    fun buscaAlunos() : List<Aluno>  {
        val sql = "SELECT * FROM Alunos;"
        val db =  readableDatabase
        val c = db.rawQuery(sql, null)

        val alunos = ArrayList<Aluno>()

        while (c.moveToNext()) {
            val aluno = Aluno ()
            aluno.nome = c.getString(c.getColumnIndex("nome"))
            aluno.endereco =  c.getString(c.getColumnIndex("endereco"))
            aluno.site = c.getString(c.getColumnIndex("site"))
            aluno.telefone = c.getString(c.getColumnIndex("telefone"))
            aluno.comentario = c.getString(c.getColumnIndex("comentario"))
            aluno.nota = c.getDouble(c.getColumnIndex("nota"))
            aluno.id = c.getLong(c.getColumnIndex("id"))

            alunos.add (aluno)
        }
        c.close()
        return alunos
    }

    fun deleta (aluno: Aluno){
        val db = writableDatabase
        val params = arrayOf(aluno.id!!.toString())
        db.delete("Alunos", "id = ?", params)
    }

    fun altera(aluno: Aluno) {
        val db = writableDatabase
        val dados = pegaDadosDoAluno(aluno)
        val params = arrayOf(aluno.id!!.toString())
        db.update("Alunos", dados, "id = ?", params)
    }
}
