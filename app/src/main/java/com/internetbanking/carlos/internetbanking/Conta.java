package com.internetbanking.carlos.internetbanking;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aluno on 18/02/2017.
 */

public class Conta implements Serializable {

    private int conta;
    private double saldo;
    private double saldoAnterior;
    private Cliente cliente;
    private ArrayList<Transacao> transacoes = new ArrayList<Transacao>();


    public Conta() {

    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Transacao> getTransacoes() {
        return transacoes;
    }

}
