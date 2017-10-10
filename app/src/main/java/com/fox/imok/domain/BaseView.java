package com.fox.imok.domain;

import com.fox.imok.domain.bd.TBContactos;

import java.util.List;

/**
 * Created by NestorSo on 28/09/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void setProgress(boolean show);
    void message(String mensaje);
    void setContactos(List<TBContactos> tbContactos);
    void finalizar();
}
