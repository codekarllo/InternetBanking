package com.internetbanking.carlos.internetbanking;

/**
 * Created by carlosrnjunior on 23/02/17.
 */
public class Saque extends Transacao implements ITransacao {
    public double get_valorSaque() {
        return _valorSaque;
    }

    private double _valorSaque;

    public Saque(double valorSaque) {
        _valorSaque = valorSaque;
    }

    @Override
    public void realizarTransacao() {
        Conta contaOrigem;
        contaOrigem = TransacaoService.getConta(this.get_numContaOrigem());
        contaOrigem.setSaldoAnterior(contaOrigem.getSaldo());
        contaOrigem.setSaldo(contaOrigem.getSaldo() - _valorSaque);
        this.setTipoOperacao(TipoOperacao.DEBITO);
        contaOrigem.getTransacoes().add(this);

        MainActivity.contas.set(MainActivity.contas.indexOf(contaOrigem), contaOrigem);
    }
}
