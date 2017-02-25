package com.internetbanking.carlos.internetbanking;

/**
 * Created by carlosrnjunior on 23/02/17.
 */

public class Transferencia extends Transacao implements ITransacao {

    private int _numContaDestino;
    private double _valorTransferencia;

    public Transferencia(int numContaDestino, double valorTransferencia) {
        _numContaDestino = numContaDestino;
        _valorTransferencia = valorTransferencia;
    }

    @Override
    public void realizarTransacao() {
        //// TODO: 23/02/17 remover dados da conta de destino para Deposito(?)
        Conta contaOrigem, contaDestino;

        contaOrigem = TransacaoService.getConta(this.get_numContaOrigem());
        contaDestino = TransacaoService.getConta(this.get_numContaDestino());

        contaOrigem.setSaldoAnterior(contaOrigem.getSaldo());
        contaDestino.setSaldoAnterior(contaDestino.getSaldo());

        contaOrigem.setSaldo(contaOrigem.getSaldo() - _valorTransferencia);
        contaDestino.setSaldo(contaDestino.getSaldo() + _valorTransferencia);

        //gambi - refatorar
        this.setTipoOperacao(TipoOperacao.DEBITO);
        contaOrigem.getTransacoes().add(this);

        this.setTipoOperacao(TipoOperacao.CREDITO);
        contaDestino.getTransacoes().add(this);

        MainActivity.contas.set(MainActivity.contas.indexOf(contaOrigem), contaOrigem);
        MainActivity.contas.set(MainActivity.contas.indexOf(contaDestino), contaDestino);
    }

    public int get_numContaDestino() {
        return _numContaDestino;
    }

    public void set_numContaDestino(int _numContaDestino) {
        this._numContaDestino = _numContaDestino;
    }

    public double get_valorTransferencia() {
        return _valorTransferencia;
    }
}
