package com.rodrigoaaenggmail.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rodrigoaaenggmail.agenda.modelo.Aluno

class AlunoDAO(context: Context) : SQLiteOpenHelper(context, "Agenda", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql =
            "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, comentario, TEXT, nota, REAL);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Alunos"
        db.execSQL(sql)
        onCreate(db)
    }

    fun insere (aluno: Aluno){
        val db = writableDatabase
        val dados: ContentValues? = null
        dados!!.put("nome", aluno.nome)
        dados!!.put("endereco", aluno.endereco)
        dados!!.put("site", aluno.site)
        dados!!.put("telefone", aluno.telefone)
        dados!!.put("note", aluno.nota)

        db.insert("Alunos", null, dados)
    }
}
