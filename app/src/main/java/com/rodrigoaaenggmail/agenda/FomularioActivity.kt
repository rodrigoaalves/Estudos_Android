package com.rodrigoaaenggmail.agenda

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
//import android.widget.EditText foi usado no começo do projeto
import android.widget.Toast
import com.rodrigoaaenggmail.agenda.dao.AlunoDAO

import kotlinx.android.synthetic.main.activity_fomulario.*

class FomularioActivity : AppCompatActivity() {

    var helper: FormularioHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fomulario)
        setSupportActionBar(toolbar)

        helper = FormularioHelper(this)

        /*val ButtonSave = findViewById<Button>(R.id.formulario_btSalvar)
        ButtonSave.setOnClickListener {
            Toast.makeText (this@FomularioActivity, "Aluno Salvo", Toast.LENGTH_SHORT).show()
            /*val VaiPraLista = Intent (this@FomularioActivity, ListaAlunosActivit::class.java)
            startActivity(VaiPraLista)**** Muito usado para criar novas activitys */
            finish()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_formulario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) when (item.itemId) {
            R.id.menu_formulario_ok -> {
                var aluno = helper!!.pegaAluno()

                // Conecta com o banco
                var dao = AlunoDAO (this)
                // faz quary para salvar o aluno
                dao.insere (aluno)
                // fecha conexão
                dao.close()
                Toast.makeText(this@FomularioActivity, "Aluno " + aluno.nome + " Salvo", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
