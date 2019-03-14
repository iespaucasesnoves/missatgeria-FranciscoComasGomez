package com.example.xisco.missatgeria;

public class Missatge {
    private String usuari;
    private String msg;
    private String datahora;

    public Missatge(String usuari, String msg, String datahora) {
        this.usuari = usuari;
        this.msg = msg;
        this.datahora = datahora;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }
}
