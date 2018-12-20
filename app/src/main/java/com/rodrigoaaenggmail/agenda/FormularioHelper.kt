package com.rodrigoaaenggmail.agenda

import android.widget.EditText
import android.widget.RatingBar


class FormularioHelper(activity: FomularioActivity) {
    private val campoNome: EditText
    private val campoEndereco: EditText
    private val campoTelefone: EditText
    private val campoSite: EditText
    private val campoComentario: EditText
    private val campoNota: RatingBar

    init {
        this.campoNome = activity.findViewById(R.id.formulario_nome) as EditText
        this.campoEndereco = activity.findViewById(R.id.formulario_endereco) as EditText
        this.campoTelefone = activity.findViewById(R.id.formulario_telefone) as EditText
        this.campoSite = activity.findViewById(R.id.formulario_site) as EditText
        this.campoComentario = activity.findViewById(R.id.formulario_comentario) as EditText
        this.campoNota = activity.findViewById(R.id.formulario_nota) as RatingBar
    }

    fun pegaAluno(): Aluno {
        val aluno = Aluno(campoNome.text.toString(), campoEndereco.text.toString(),
            campoSite.text.toString(), campoTelefone.text.toString(), campoComentario.text.toString(),
            java.lang.Double.valueOf(campoNota.progress.toDouble()))
        return aluno
    }
}