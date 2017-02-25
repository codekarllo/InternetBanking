package com.internetbanking.carlos.internetbanking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaqueActivity extends AppCompatActivity {

    private EditText edtValorSaque;
    private Button btnRealizarSaque;
    private View.OnClickListener onClickHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saque);
        setTitle("Saque Conta Corrente");


        onClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valorSaque = Double.parseDouble(edtValorSaque.getText().toString());
                if(v == btnRealizarSaque){
                    if(edtValorSaque.getText().length() > 0){
                        if(TransacaoService.getContaAtual().getSaldo() >= valorSaque){
                            Transacao t = new Saque(valorSaque);
                            t.set_numContaOrigem(TransacaoService.getContaAtual().getConta());
                            ((Saque) t).realizarTransacao();
                            Toast.makeText(SaqueActivity.this, "Saque realizado!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(SaqueActivity.this, getString(R.string.saldo_insuficiente), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        };

        edtValorSaque = (EditText) findViewById(R.id.edtValorSaque);
        btnRealizarSaque = (Button) findViewById(R.id.btnRealizarSaque);
        btnRealizarSaque.setOnClickListener(onClickHandler);


    }
}
