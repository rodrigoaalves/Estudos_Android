package com.rodrigoaaenggmail.agenda.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.rodrigoaaenggmail.agenda.ListaAlunosActivit;
import com.rodrigoaaenggmail.agenda.R;
import com.rodrigoaaenggmail.agenda.modelo.Aluno;

import java.util.List;

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Aluno aluno = alunos.get(position);
        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        View campoNome = (TextView) view.findViewById (R.id.item_nome);
        ((TextView) campoNome).setText (aluno.getNome());

        View campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        ((TextView) campoTelefone).setText (aluno.getTelefone());

        View campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            ((ImageView) campoFoto).setImageBitmap(bitmapReduzido);
            campoFoto.setTag(caminhoFoto);
            ((ImageView) campoFoto).setScaleType(ImageView.ScaleType.FIT_XY);
        }


        return view;
    }
}
