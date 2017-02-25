package com.internetbanking.carlos.internetbanking;

import android.icu.text.DecimalFormat;

/**
 * Created by carlosrnjunior on 21/02/17.
 */

public class TransacaoService {
    public static Conta getContaAtual() {
        return contaAtual;
    }

    //// TODO: 24/02/17
    private static Conta contaAtual;

    public static Conta getConta(int numConta) {

        for (Conta conta : MainActivity.contas) {
            int num = conta.getConta();
            if (num == numConta) {
                contaAtual = conta;
                return getContaAtual();
            }
        }
        return null;
    }

    public static String formataValor(double valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(valor);
    }
}
