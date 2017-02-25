package com.internetbanking.carlos.internetbanking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

public class AbrirContaActivity extends AppCompatActivity {

    private Cliente cliente;
    private Conta conta;
    private Button btnAbrirConta;
    private EditText edtConta;
    private EditText edtValor;
    private TextView txtWelcome;
    private ProgressDialog nDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_conta);
        setTitle("Abrir Conta");

        inicializaComponentes();

        final DecimalFormat df = new DecimalFormat("#,##0.00");


        edtValor.addTextChangedListener(new TextWatcher() {
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* edtValor.removeTextChangedListener(this);
                current = df.format(Double.parseDouble(edtValor.getText().toString()));
                edtValor.setText(current);
                edtValor.setSelection(current.length());
                edtValor.addTextChangedListener(this);*/
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        btnAbrirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conta = new Conta();
                conta.setCliente(cliente);
                conta.setSaldo(Double.parseDouble(edtValor.getText().toString()));
                conta.setConta(MainActivity.numConta);

                MainActivity.contas.add(conta);
                MainActivity.numConta++;

                Intent intent = new Intent(AbrirContaActivity.this, ContaActivity.class);
                intent.putExtra("conta", conta);
                Toast.makeText(getApplicationContext(), "Conta criada com sucesso!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(intent);

            }
        });

        Bundle extra = getIntent().getExtras();

        if (extra.get("cliente") != null) {
            cliente = (Cliente) extra.get("cliente");
            txtWelcome.setText("Olá, " + cliente.getNome() + " o número da sua conta é: ");
        }
    }

    private void inicializaComponentes() {
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        btnAbrirConta = (Button) findViewById(R.id.btnAbrirConta);
        edtConta = (EditText) findViewById(R.id.edtNumConta);
        edtConta.setText(String.format("%05d",MainActivity.numConta));
        edtValor = (EditText) findViewById(R.id.edtValor);

    }


}
