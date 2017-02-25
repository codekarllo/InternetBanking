package com.internetbanking.carlos.internetbanking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by carlosrnjunior on 23/02/17.
 */

public class TransferenciaActivity extends AppCompatActivity {

    private Button btnRealizarTransferencia;
    private EditText edtContaDestino;
    private EditText edtValorTransferencia;
    private TextView txtInfoConta;
    private View.OnClickListener onClickHandler;
    private Conta conta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);
        setTitle(getString(R.string.titulo_transferencia_activity));

        inicializaComponentes();
    }

    private void inicializaComponentes() {

        Bundle extra = getIntent().getExtras();
        conta = (Conta) extra.get("conta");

        onClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnRealizarTransferencia) {
                    if (edtContaDestino.getText().length() > 0 && edtValorTransferencia.getText().length() > 0) {
                        if (!txtInfoConta.getText().toString().equals(getString(R.string.conta_inexistente))) {
                            int numContaDestino = Integer.parseInt(edtContaDestino.getText().toString());
                            if(conta.getConta() != numContaDestino) {
                                double valorTransferencia = Double.parseDouble(edtValorTransferencia.getText().toString());

                                if (conta.getSaldo() >= valorTransferencia) {
                                    realizarTransacao(numContaDestino, valorTransferencia);

                                    AlertDialog dialog = gerarDialog();
                                    dialog.show();

                                } else {
                                    Toast.makeText(TransferenciaActivity.this, getString(R.string.saldo_insuficiente), Toast.LENGTH_LONG).show();
                                }
                            }else {
                                    Toast.makeText(TransferenciaActivity.this, "Operação não permitida.", Toast.LENGTH_LONG).show();
                                }

                        } else {
                            Toast.makeText(TransferenciaActivity.this, getString(R.string.conta_valida), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(TransferenciaActivity.this, getString(R.string.campos_obrigatorios), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        btnRealizarTransferencia = (Button) findViewById(R.id.btnRealizarTransferencia);
        btnRealizarTransferencia.setOnClickListener(onClickHandler);
        edtContaDestino = (EditText) findViewById(R.id.edtContaDestino);
        edtValorTransferencia = (EditText) findViewById(R.id.edtValorTransferencia);
        txtInfoConta = (TextView) findViewById(R.id.txtInfoConta);
        txtInfoConta.setElegantTextHeight(true);
        txtInfoConta.setSingleLine(false);
        txtInfoConta.setLines(3);
        txtInfoConta.setTextSize(18);

        edtContaDestino.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtContaDestino.getText().length() > 0) {
                    Conta contaDestino = TransacaoService.getConta(Integer.parseInt(edtContaDestino.getText().toString()));
                    if (contaDestino != null) {
                        txtInfoConta.setText("Favorecido: " + contaDestino.getCliente().getNome() +
                                "\nCPF: " + contaDestino.getCliente().getCpf() +
                                " \nAgência: 00001 / Conta: " + String.format("%05d", contaDestino.getConta()));
                    } else {
                        txtInfoConta.setText("Conta inexistente!");
                    }
                } else {
                    txtInfoConta.setText("");
                }
            }
        });
    }

    private void realizarTransacao(int numContaDestino, double valorTransferencia) {
        Transacao t = new Transferencia(numContaDestino, valorTransferencia);
        if (t != null) {
            t.set_numContaOrigem(conta.getConta());
            ((Transferencia) t).realizarTransacao();
        }
    }

    private AlertDialog gerarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TransferenciaActivity.this);
        builder.setMessage(getString(R.string.transferencia_sucesso)).setTitle(getString(R.string.aviso));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TransferenciaActivity.this, ContaActivity.class);
                intent.putExtra("conta", TransacaoService.getConta(conta.getConta()));
                finish();
                startActivity(intent);
            }
        });
        return builder.create();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void finish() {
        Intent intent = new Intent(TransferenciaActivity.this, ContaActivity.class);
        intent.putExtra("conta", TransacaoService.getConta(conta.getConta()));
        setResult(Activity.RESULT_CANCELED, intent);
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
