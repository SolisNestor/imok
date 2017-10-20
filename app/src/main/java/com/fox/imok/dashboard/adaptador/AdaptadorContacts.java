package com.fox.imok.dashboard.adaptador;

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
 * Created by NestorSo on 11/10/2017.
 */

public class AdaptadorContacts extends RecyclerView.Adapter<AdaptadorContacts.ContactsHolder> {


    private List<TBContactos> tbContactos;

    public AdaptadorContacts(List<TBContactos> tbContactos) {
        this.tbContactos = tbContactos;
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cell,parent, false);
        ContactsHolder contactsHolder = new ContactsHolder(v);
        return contactsHolder;
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        holder.bindHolder(tbContactos.get(position));
    }

    @Override
    public int getItemCount() {
        return tbContactos.size();
    }

    class ContactsHolder extends RecyclerView.ViewHolder{

        TextView txtUserName;
        TextView txtUserPhone;
        ImageButton btnCall;
        ImageButton btnCheck;
        public ContactsHolder(View itemView) {
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.user_name);
            txtUserPhone = (TextView) itemView.findViewById(R.id.user_phone);
            btnCall =(ImageButton) itemView.findViewById(R.id.user_act_call);
            btnCheck =(ImageButton) itemView.findViewById(R.id.user_act_ok);
        }

        public void bindHolder(TBContactos tbContacto){
            if(tbContacto.isOk()) {
                btnCheck.setVisibility(View.VISIBLE);
                btnCall.setVisibility(View.GONE);
            }else {
                btnCheck.setVisibility(View.GONE);
                btnCall.setVisibility(View.VISIBLE);
            }

            txtUserPhone.setText(tbContacto.getTelefono());
            txtUserName.setText(tbContacto.getNombre());
        }
    }

    public List<TBContactos> getTbContactos() {
        return tbContactos;
    }

    public void setTbContactos(List<TBContactos> tbContactos) {
        this.tbContactos = tbContactos;
        notifyDataSetChanged();
    }
}
