package com.internetbanking.carlos.internetbanking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroClienteActivity extends AppCompatActivity {

    private Cliente cliente;
    private EditText edtCpf;
    private EditText edtNomeCliente;
    private EditText edtEmailCliente;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        setTitle("Abrir Conta");

        inicializaComponentes();

        edtCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtCpf.getText().length() == 11)
                        edtCpf.setText(formataCPF());
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validaDados()) {

                } else {
                    cliente = new Cliente();
                    cliente.setCpf(edtCpf.getText().toString());
                    cliente.setNome(edtNomeCliente.getText().toString());
                    cliente.setEmail(edtEmailCliente.getText().toString());

                    MainActivity.clientes.add(cliente);

                    Intent intent = new Intent(CadastroClienteActivity.this, AbrirContaActivity.class);
                    intent.putExtra("cliente", cliente);

                    Toast.makeText(getApplicationContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    //Método rápido pra formatar CPF (firula)
    private String formataCPF() {

        char[] cpf = new char[14];
        char aux = ' ';
        int pos = 0;
        boolean flag = false;

        for (int i = 0; i < cpf.length; i++) {

            if (i == 3 || i == 7 || i == 11) {
                if (i == 11) {
                    cpf[i] = '-';
                    aux = edtCpf.getText().toString().charAt(i - pos);
                    cpf[++i] = aux;
                    pos = 3;
                    continue;
                } else {
                    cpf[i] = '.';
                }

                if (flag) {
                    aux = edtCpf.getText().toString().charAt(i - pos);
                    pos = 2;
                } else {
                    aux = edtCpf.getText().toString().charAt(i);
                    flag = true;
                    pos = 1;
                }
                cpf[++i] = aux;
            } else {
                if (flag) {
                    cpf[i] = edtCpf.getText().toString().charAt(i - pos);

                } else {
                    cpf[i] = edtCpf.getText().toString().charAt(i);
                }
            }
        }

        String cpfFormatado = new String(cpf);

        return cpfFormatado;
    }

    private void inicializaComponentes() {
        edtCpf = (EditText) findViewById(R.id.edtCPF);
        edtNomeCliente = (EditText) findViewById(R.id.edtNomeCliente);
        edtEmailCliente = (EditText) findViewById(R.id.edtEmailCliente);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
    }

    private boolean validaDados() {
        if (edtCpf.getText().length() != 14) {
            Toast.makeText(getApplicationContext(), "CPF Inválido!", Toast.LENGTH_LONG).show();
            return false;
        } else if (edtNomeCliente.getText().toString().equals("Nome Completo") || edtNomeCliente.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Nome Inválido!", Toast.LENGTH_LONG).show();
            return false;
        } else if (edtEmailCliente.getText().toString().equals("E-mail") || edtEmailCliente.length() == 0) {
            Toast.makeText(getApplicationContext(), "E-mail Inválido", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
