package com.fox.imok.reporte;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fox.imok.R;
import com.fox.imok.domain.bd.TBContactos;

import java.util.List;

/**
 * Created by NestorSo on 25/10/2017.
 */

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.Holder> {
    private OnClickContacto listener;
    private List<TBContactos> listaContactos;
    public AdapterLista(List<TBContactos> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cell, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bindHolder(listaContactos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }


    public void setListaContactos(List<TBContactos> listaContactos) {
        this.listaContactos = listaContactos;
        notifyDataSetChanged();
    }

    public List<TBContactos> getListaContactos() {
        return listaContactos;
    }

    public OnClickContacto getListener() {
        return listener;
    }

    public void setListener(OnClickContacto listener) {
        this.listener = listener;
    }

    interface OnClickContacto{
        void call(TBContactos contacto);
    }
    class Holder extends RecyclerView.ViewHolder {

        private TextView txtNombre;
        private TextView txtTelefono;
        public ImageButton btnLlamar;
        private ImageButton btnOk;

        public Holder(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.user_name);
            txtTelefono = (TextView) itemView.findViewById(R.id.user_phone);
            btnLlamar = (ImageButton) itemView.findViewById(R.id.user_act_call);
            btnOk = (ImageButton) itemView.findViewById(R.id.user_act_ok);
        }

        public void bindHolder(final TBContactos contacto) {
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            btnOk.setVisibility(contacto.isOk() ? View.VISIBLE : View.GONE);
            btnLlamar.setVisibility(contacto.isOk() ? View.GONE : View.VISIBLE);
            btnLlamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if(getListener()!=null)
                            getListener().call(contacto);
                }
            });
        }
    }
}
