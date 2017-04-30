package com.trairas.nig.sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.trairas.nig.sort.Util.OperArquivos;
import java.util.Random;

//cliente
import android.app.ProgressDialog;
import android.content.Context;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class _main extends AppCompatActivity {

    Button bt_1;
    EditText ed_1;
    EditText ed_2;

    String[] Lista;
    final boolean[] _verResultados = {false};
    final boolean[] _gerar_numeros = {true};
    final boolean[] _enviar = {false};
    String Numeros = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);




        bt_1 = (Button) findViewById(R.id.m_bt_1);
        bt_1.setText("Gerar Numeros");

        ed_1 = (EditText) findViewById(R.id.m_ed_1);
        ed_1.setText("10");
        ed_2 = (EditText) findViewById(R.id.m_ed_2);
        ed_2.setText("50");

        //Action Bar


        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (_enviar[0]){
                    print(" iniciando cliente ");
                    try{
                        new Cliente(Lista, _main.this).start();
                    }
                    catch (Exception erro){
                        Toast.makeText(_main.this, "Servidor Indisponível", Toast.LENGTH_SHORT).show();
                    }

                }


                if(_gerar_numeros[0]){
                    print("Gerando Numeros para Ordenação");
                    int[] val = valida(ed_1.getText().toString(),ed_2.getText().toString());
                    gerarNumeros(val[0], val[1]);
                    _enviar[0] = true;
                    _gerar_numeros[0] = false;
                    _verResultados[0] = false;
                    bt_1.setText("Enviar");
                }


                if(_verResultados[0]){
                    print("Chamando uma nova tela para ver os resultados > ");
                    Intent in = new Intent(_main.this, resultados.class);
                    startActivity(in);
                }

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.action_settings){
            Intent in = new Intent(_main.this, settings.class);
            startActivity(in);
        }
        if (item.getItemId() == R.id.action_ver_numeros){
            if(Numeros.length()==0){
                Alerta("Números",  "Não foram Gerados Números");
            }
            else{
                Alerta("Números gerados",  Numeros);
            }
        }
        if (item.getItemId() == R.id.action_new_sort){

            if(Lista != null){
                for(int i=0;i<Lista.length;i++){
                    Lista[i] = null;
                }
            }

            _verResultados[0] = false;
            _gerar_numeros[0] = true;
            _enviar[0] = false;
            Numeros = "";

            bt_1.setText("Gerar Numeros");
            ed_1.setText("10");
            ed_2.setText("50");


        }

        return true;
    }


    /**----------------Minhas funcoes-----------*/

    private int[] valida(String numeros, String maximo){

        int[] val = new int[2];

        try {
            int num = Integer.parseInt(numeros);
            val[0] = num;
        }catch (Exception e){
            print("numeros nao inteiro");
            val[0] = 0;
        }

        try {
            int num = Integer.parseInt(maximo);
            val[1] = num;
        }catch (Exception e){
            print("numeros nao inteiro");
            val[1] = 0;
        }

        return val;
    }

    private void gerarNumeros(int numeros, int maximo){

        if (numeros != 0 && maximo != 0){

            Random r = new Random();
            Lista = new String[numeros];

            for (int i =0; i < numeros; i++){
                int x = r.nextInt(maximo+1);
                Lista[i] = x+"";
            }

            print("Numeros gerados com Sucesso!");

            Numeros = "";
            for (int i =0; i < numeros; i++){
                print("lista["+i+"] = " + Lista[i]);
                Numeros += Lista[i]+", ";
            }



            final AlertDialog.Builder alerta = new AlertDialog.Builder(_main.this);
            alerta.setTitle("Números Gerados");
            alerta.setMessage(Numeros);
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  print("Numeros gerados!");
                }
            });

            alerta.show();

        }


    }

    private void Alerta(String title, String mensagem){
        final AlertDialog.Builder alerta = new AlertDialog.Builder(_main.this);
        alerta.setTitle(title);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alerta.show();
    }

    public void print(String m){
        Log.v("< ---- SORT ----> ", m);
    }





    /**======================CLIENTE======================================*/

    public class Cliente extends Thread{

        String lista;
        Context contento;
        ProgressDialog progress;


        public Cliente(){}

        public Cliente(String[] lis, Context contexto){

            this.contento = contexto;

            String[] list = lis;
            for(int i =0;i < list.length;i++){
                if(i+1 == list.length){
                    if(list[i] != null){
                        lista += list[i]+".";
                    }
                    print("lista["+i+"] = "+list[i]);
                }
                else{
                    if(list[i] != null){
                        lista += list[i]+",";
                    }

                }
            }

            progress = new ProgressDialog(contexto);
            progress.setTitle("Ordenação");
            progress.setMessage("Ordenando Numeros no Servidor");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setIcon(R.drawable.icone_app);
            progress.setCanceledOnTouchOutside(false);

        }



        @Override
        public void run(){

            print("Iniciada a Thread");

            //iniciar um loop com um contador para barra de prograsso esperando uma mensagem do servidor
            OperArquivos opr = new OperArquivos();
            String ip = opr.ler(contento,"_ip.txt");
            int porta = Integer.parseInt(opr.ler(contento, "_porta.txt"));

            try{
                print("Iniciando conexão com o servidor > ");
                print("iniciando com ip = "+ip+" porta = "+porta);
                Socket socket = new Socket(ip, porta);
                print("Comunicaão feita com sucesso!");

                _main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.show();
                    }
                });


                ObjectOutputStream enviar = new ObjectOutputStream(socket.getOutputStream());
                enviar.flush();
                enviar.writeObject(lista);
                print("lista enviada");


                ObjectInputStream receber = new ObjectInputStream(socket.getInputStream());
                String lista_ordeanda_servidor = "";
                lista_ordeanda_servidor = (String) receber.readObject();
                print("Lista Ordenada do SERVIDOR = "+lista_ordeanda_servidor);
                opr.salvar(lista_ordeanda_servidor, contento, "ordenada.txt");



                String tempo_qs = (String) receber.readObject();
                print("Tempo QuickSort = "+tempo_qs);
                opr.salvar(tempo_qs, contento, "qs.txt");

                String tempo_ss = (String) receber.readObject();
                print("Tempo Shell = "+tempo_ss);
                opr.salvar(tempo_ss, contento, "ss.txt");

                print("Fechando a conexão > ");
                socket.close();
                print("Conexao fechada! \n habilitando recursos para ver os resultados");

                _verResultados[0] = true;
                _gerar_numeros[0] = false;
                _enviar[0] = false;
                bt_1.setText("Ver resultados");
                bt_1.setBackgroundResource(R.color.color_verde);

                //deixa o botao verde pedindo pra chamar outra activity para ver os resultados

                progress.dismiss();



            }catch (Exception e){
                progress.dismiss();

                _main.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(contento, "Servidor Indisponível", Toast.LENGTH_SHORT).show();
                    }
                });


                //escrever  no arquivo off
                print("Erro ao iniciar socket cliente >");
            }

        }

        public void print(String m){
            Log.v("< ---- CLIENTE ----> ", m);
        }

    }


}
