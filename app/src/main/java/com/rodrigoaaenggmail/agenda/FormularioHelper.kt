package com.rodrigoaaenggmail.agenda

import android.view.WindowId
import android.widget.EditText
import android.widget.RatingBar
import com.rodrigoaaenggmail.agenda.modelo.Aluno


class FormularioHelper(activity: FomularioActivity) {
    private val campoNome: EditText
    private val campoEndereco: EditText
    private val campoTelefone: EditText
    private val campoSite: EditText
    private val campoComentario: EditText
    private val campoNota: RatingBar
    private var aluno = Aluno()


    init {
        campoNome = activity.findViewById(R.id.formulario_nome) as EditText
        campoEndereco = activity.findViewById(R.id.formulario_endereco) as EditText
        campoTelefone = activity.findViewById(R.id.formulario_telefone) as EditText
        campoSite = activity.findViewById(R.id.formulario_site) as EditText
        campoComentario = activity.findViewById(R.id.formulario_comentario) as EditText
        campoNota = activity.findViewById(R.id.formulario_nota) as RatingBar
    }

    fun pegaAluno(): Aluno {

        aluno.nome = campoNome.text.toString()
        aluno.endereco = campoEndereco.text.toString()
        aluno.site = campoSite.text.toString()
        aluno.telefone = campoTelefone.text.toString()
        aluno.comentario = campoComentario.text.toString()
        aluno.nota = java.lang.Double.valueOf(campoNota.progress.toDouble())

        return aluno
    }

    fun preencheFormulario(aluno: Aluno) {
        campoNome.setText(aluno.nome)
        campoEndereco.setText(aluno.endereco)
        campoComentario.setText(aluno.comentario)
        campoSite.setText(aluno.site)
        campoTelefone.setText(aluno.telefone)
        campoNota.progress =  aluno.nota!!.toInt()
        this.aluno = aluno
    }
}