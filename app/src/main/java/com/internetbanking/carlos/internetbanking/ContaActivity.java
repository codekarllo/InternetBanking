package com.internetbanking.carlos.internetbanking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContaActivity extends AppCompatActivity {


    private Button ibTransferencia;
    private Button ibSaldo;
    private Button ibExtrato;
    private Button ibSaque;
    private TextView txtInfo;
    private TextView txtWelcome;
    private TextView txtSaldo;

    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conta);
        setTitle("Minha Conta");
        inicializarComponentes();

        ibTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContaActivity.this, TransferenciaActivity.class);
                intent.putExtra("conta", conta);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                finish();
                startActivityForResult(intent, 1);

            }
        });

        ibSaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ContaActivity.this,SaqueActivity.class);
                startActivity(intent);

            }
        });

        ibExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transacao t = new Extrato();
                t.set_numContaOrigem(conta.getConta());
                ((Extrato) t).realizarTransacao();

                AlertDialog.Builder builder = new AlertDialog.Builder(ContaActivity.this);

                if (((Extrato) t).getItemsExtrato().length > 0) {
                    atualizaDados();

                    builder.setItems(((Extrato) t).getItemsExtrato(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setTitle("Extrato");

                } else {
                    builder.setMessage("Nenhuma transação encontrada.")
                            .setTitle("Extrato");
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ibSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContaActivity.this);
                builder.setMessage("Saldo disponível R$" + formataValor(conta.getSaldo()))
                        .setTitle("Saldo");
                builder.create();
                builder.show();
            }
        });

        if (getIntent().getExtras() != null) {
            Bundle extra = getIntent().getExtras();
            if (extra.get("conta") != null) {
                conta = (Conta) extra.get("conta");

                txtWelcome.setText(conta.getCliente().getNome());
                txtInfo.setText("Agência: 01 / Conta: " + String.format("%05d", conta.getConta()));
                txtSaldo.setText("Saldo: R$ " + formataValor(conta.getSaldo()));
            }
        } else {
        }
    }

    private void atualizaDados() {
        AtualizaDadosTask task = new AtualizaDadosTask();
        task.execute(conta.getConta());
    }

    private void inicializarComponentes() {
        ibTransferencia = (Button) findViewById(R.id.ibTransferencia);
        ibSaldo = (Button) findViewById(R.id.ibSaldo);
        ibExtrato = (Button) findViewById(R.id.ibExtrato);
        ibSaque = (Button) findViewById(R.id.ibSaque);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtSaldo = (TextView) findViewById(R.id.txtSaldo);
    }

    private String formataValor(double valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(valor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            Bundle extra = getIntent().getExtras();
            if (extra.get("conta") != null) {
                conta = (Conta) extra.get("conta");

                txtWelcome.setText(conta.getCliente().getNome());
                txtInfo.setText("Agência: 01 / Conta: " + String.format("%05d", conta.getConta()));
                txtSaldo.setText("Saldo: R$ " + formataValor(conta.getSaldo()));

            }
        }
        atualizaDados();
    }

    //Classe para atualizar o saldo assincronamente
    private class AtualizaDadosTask extends AsyncTask<Integer, Void, Double> {

        @Override
        protected Double doInBackground(Integer... params) {
            Conta conta = TransacaoService.getConta(params[0]);
            return conta.getSaldo();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            conta.setSaldo(aDouble);
            txtSaldo.setText("Saldo: R$ " + formataValor(conta.getSaldo()));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
                conta = (Conta) data.getExtras().get("conta");
                txtWelcome.setText(conta.getCliente().getNome());
                txtInfo.setText("Agência: 01 / Conta: " + String.format("%05d", conta.getConta()));
                txtSaldo.setText("Saldo: R$ " + formataValor(conta.getSaldo()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelecionado = item.getItemId();
        if (itemSelecionado == R.id.action_exit) {

            finish();


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {

        super.finish();
    }
}
