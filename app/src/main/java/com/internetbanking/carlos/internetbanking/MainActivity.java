package com.internetbanking.carlos.internetbanking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Conta> contas = new ArrayList<>();
    public static int numConta = 0001;
    private Cliente cliente;
    private Conta contaAtual;
    private Button btnGotoCadastrarCliente;
    private Button btnLogin;
    private EditText edtConta;
    private View.OnClickListener onClickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.titulo);

        inicializaComponentes();
    }

    private void inicializaComponentes() {
        fillContas();

        onClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()){
                    case R.id.btnGotoCadastroCliente:
                         intent = new Intent(MainActivity.this,CadastroClienteActivity.class);
                        break;

                    case R.id.btnLogin :
                        contaAtual = getConta();
                        if(contaAtual != null){
                            intent = new Intent(MainActivity.this, ContaActivity.class);
                            intent.putExtra("conta", contaAtual);
                            edtConta.getText().clear();

                            break;
                        }
                    default:

                }
                if(intent != null){
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Insira uma conta válida!", Toast.LENGTH_LONG).show();
                }
            }
        };

        btnGotoCadastrarCliente = (Button) findViewById(R.id.btnGotoCadastroCliente);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtConta = (EditText) findViewById(R.id.edtConta);

        btnGotoCadastrarCliente.setOnClickListener(onClickHandler);
        btnLogin.setOnClickListener(onClickHandler);
    }

    private Conta getConta() {
        int numContaAtual = 0;
        if(edtConta.getText().length() > 0 ){
            numContaAtual = Integer.parseInt(edtConta.getText().toString());
            return TransacaoService.getConta(numContaAtual);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelecionado = item.getItemId();
        if(itemSelecionado == R.id.action_exit){
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillContas(){
        Cliente cliente1 = new Cliente(), cliente2 = new Cliente();

        cliente1.setNome("Carlos Roberto");
        cliente1.setCpf("001.222.333-40");
        cliente1.setEmail("carlos.cstds@gmail.com");
        clientes.add(cliente1);

        cliente2.setNome("Maria Moura");
        cliente2.setCpf("147.254.987-45");
        cliente2.setEmail("maria@gmail.com");
        clientes.add(cliente2);

        Conta conta1 = new Conta(),conta2 = new Conta();

        conta1.setCliente(cliente1);
        conta1.setSaldo(1500.0);
        conta1.setSaldoAnterior(conta1.getSaldo());
        conta1.setConta(MainActivity.numConta++);
        contas.add(conta1);

        conta2.setCliente(cliente2);
        conta2.setSaldo(100.0);
        conta2.setSaldoAnterior(conta2.getSaldo());
        conta2.setConta(MainActivity.numConta++);
        contas.add(conta2);
    }
}
