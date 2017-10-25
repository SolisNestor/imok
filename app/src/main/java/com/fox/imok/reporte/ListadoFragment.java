package com.fox.imok.reporte;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fox.imok.R;
import com.fox.imok.dashboard.eventbus.ContactoEventBus;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.models.Error;
import com.fox.imok.domain.models.Progreso;
import com.fox.imok.domain.permisos.PermisoObj;
import com.fox.imok.domain.permisos.Permisos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ListadoFragment extends Fragment implements ContratoReporte.View {

    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.btnReenviarAlerta)
    Button btnReenviarAlerta;
    private AdapterLista adapterLista;
    private ContratoReporte.Presenter presenter;
    private Permisos permisos;
    @BindView(R.id.listaUsuarios)
    RecyclerView listaUsuarios;
    Unbinder unbinder;


    private int tab;


    public ListadoFragment() {
        // Required empty public constructor
    }


    public static ListadoFragment newInstance(int tab) {
        ListadoFragment fragment = new ListadoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, tab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            tab = getArguments().getInt(ARG_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listado, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ListadoPresenter(this);
        List<TBContactos> contactos = new ArrayList<>();
        adapterLista = new AdapterLista(contactos);
        listaUsuarios.setHasFixedSize(true);
        listaUsuarios.setAdapter(adapterLista);
        listaUsuarios.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        listaUsuarios.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listaUsuarios.setItemAnimator(new DefaultItemAnimator());
        presenter.buscarContactos(tab);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setContactos(List<TBContactos> tbContactos) {
        adapterLista.setListaContactos(tbContactos);
        adapterLista.setListener(presenter.getListener());
        showReenviarSMS();

    }

    @Override
    public void showError(Error error) {
        Toast.makeText(getContext(), error.getMensaje(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void showProgress(Progreso progreso) {
        if(progreso.getProgress()>=0) {
            btnReenviarAlerta.setText(getString(R.string.enviandoalertas));
            btnReenviarAlerta.setEnabled(false);
        }else{
            btnReenviarAlerta.setText(getString(R.string.reeviaralertas));
            btnReenviarAlerta.setEnabled(true);
        }
    }

    @Override
    public void showPermission(String permiso) {
        PermisoObj permisosObj = new PermisoObj(new String[]{
                permiso
        }, 1);
        permisos = new Permisos(getActivity(), permisosObj);
        permisos.solicitarPermisos();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recargarDatos(ContactoEventBus contactoEventBus) {
        presenter.buscarContactos(tab);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permisos.onRequestPermissionsResult(requestCode, permissions, grantResults))
            showMessage(getString(R.string.continuarahora));
        else
            showMessage(getString(R.string.aceptarpermisos));

    }
    private void showReenviarSMS() {
        boolean show = tab==3;
        if(adapterLista == null || adapterLista.getListaContactos().size()==0)
            show=false;
        btnReenviarAlerta.setVisibility(show?View.VISIBLE:View.GONE);
        btnReenviarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.reenviarSMS();
            }
        });
    }
}
