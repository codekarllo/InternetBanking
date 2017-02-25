package com.internetbanking.carlos.internetbanking;

import java.util.ArrayList;

/**
 * Created by carlosrnjunior on 23/02/17.
 */

public class Extrato extends Transacao implements ITransacao {

    private ArrayList<Transacao> extrato = new ArrayList<Transacao>();

    public CharSequence[] getItemsExtrato() {
        return itemsExtrato;
    }

    private CharSequence[] itemsExtrato;

    public ArrayList<Transacao> getExtrato() {
        return extrato;
    }

    @Override
    public void realizarTransacao() {
        Conta conta = TransacaoService.getConta(this.get_numContaOrigem());

        if(conta.getTransacoes().size() >= 5){
            extrato =  new ArrayList<Transacao> (conta.getTransacoes().subList(conta.getTransacoes().size() -5, conta.getTransacoes().size()  ));
        }else{
            extrato = conta.getTransacoes();
        }

        itemsExtrato = new CharSequence[getExtrato().size()];

        for (int i = 0; i < getExtrato().size(); i++) {

            if (getExtrato().get(i) instanceof Transferencia) {
                Transferencia t = (Transferencia) getExtrato().get(i);

                String tipoOperacao = conta.getConta() == t.get_numContaOrigem() ? " D" : " C";
                String nomeCliente = tipoOperacao.equals(" d") ?
                        TransacaoService.getConta(t.get_numContaDestino()).getCliente().getNome() :
                        TransacaoService.getConta(t.get_numContaOrigem()).getCliente().getNome();

                itemsExtrato[i] = "Transferência online | Ag 00001 / " +
                        String.format("%05d", tipoOperacao.equals(" D") ? t.get_numContaDestino() : t.get_numContaOrigem()) +
                        " | " + nomeCliente +
                        " | Valor: R$ " + TransacaoService.formataValor(t.get_valorTransferencia()) + tipoOperacao;

            } else if (getExtrato().get(i) instanceof Saque) {
                Saque s = (Saque) getExtrato().get(i);
                String tipoOperacao = " D";
                itemsExtrato[i] = "Saque | Ag 00001 / " +
                        String.format("%05d", s.get_numContaOrigem()) +
                        " | " + conta.getCliente().getNome() +
                        " | Valor: R$ " + TransacaoService.formataValor(s.get_valorSaque()) + tipoOperacao;

            }
        }
    }
}
