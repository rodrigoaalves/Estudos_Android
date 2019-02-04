package com.rodrigoaaenggmail.agenda

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import com.rodrigoaaenggmail.agenda.modelo.Aluno




class FormularioHelper(activity: FomularioActivity) {
    private val campoNome: EditText
    private val campoEndereco: EditText
    private val campoTelefone: EditText
    private val campoSite: EditText
    private val campoComentario: EditText
    private val campoNota: RatingBar
    private val campoFoto: ImageView
    private var aluno = Aluno()


    init {
        campoNome = activity.findViewById(R.id.formulario_nome) as EditText
        campoEndereco = activity.findViewById(R.id.formulario_endereco) as EditText
        campoTelefone = activity.findViewById(R.id.formulario_telefone) as EditText
        campoSite = activity.findViewById(R.id.formulario_site) as EditText
        campoComentario = activity.findViewById(R.id.formulario_comentario) as EditText
        campoNota = activity.findViewById(R.id.formulario_nota) as RatingBar
        campoFoto = activity.findViewById(R.id.formulario_foto) as ImageView
    }

    fun pegaAluno(): Aluno {

        aluno.nome = campoNome.text.toString()
        aluno.endereco = campoEndereco.text.toString()
        aluno.site = campoSite.text.toString()
        aluno.telefone = campoTelefone.text.toString()
        aluno.comentario = campoComentario.text.toString()
        aluno.nota = java.lang.Double.valueOf(campoNota.progress.toDouble())
        aluno.CaminhoFoto = campoFoto.tag as String?

        return aluno
    }

    fun preencheFormulario(aluno: Aluno) {
        campoNome.setText(aluno.nome)
        campoEndereco.setText(aluno.endereco)
        campoComentario.setText(aluno.comentario)
        campoSite.setText(aluno.site)
        campoTelefone.setText(aluno.telefone)
        campoNota.progress =  aluno.nota!!.toInt()
        CarregaImagem(aluno.CaminhoFoto)
        this.aluno = aluno
    }

    fun CarregaImagem (caminhoFoto: String?)
    {
        if (caminhoFoto != null) {
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)
            val bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
            campoFoto.setImageBitmap(bitmapReduzido)
            campoFoto.setTag(caminhoFoto)
            campoFoto.scaleType = ImageView.ScaleType.FIT_XY
            campoFoto.setTag(caminhoFoto)
        }
    }
}