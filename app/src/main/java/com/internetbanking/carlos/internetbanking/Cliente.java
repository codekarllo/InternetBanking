package com.internetbanking.carlos.internetbanking;

import java.io.Serializable;

/**
 * Created by aluno on 18/02/2017.
 */

public class Cliente implements Serializable {

    private String cpf;
    private String nome;
    private String email;

    public Cliente(String cpf, String email) {
        this.cpf = cpf;
        this.email = email;
    }

    public Cliente() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
