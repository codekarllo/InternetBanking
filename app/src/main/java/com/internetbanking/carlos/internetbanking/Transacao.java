package com.internetbanking.carlos.internetbanking;

import java.io.Serializable;

/**
 * Created by carlosrnjunior on 23/02/17.
 */

public class Transacao implements Serializable {

    private int _numContaOrigem;
    private TipoOperacao tipoOperacao;


    public Transacao(int numContaOrigem) {
        _numContaOrigem = numContaOrigem;
    }

    public Transacao() {

    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public int get_numContaOrigem() {
        return _numContaOrigem;
    }

    public void set_numContaOrigem(int numContaOrigem) {
        this._numContaOrigem = numContaOrigem;
    }
}
