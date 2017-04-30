package com.trairas.nig.sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class _main extends AppCompatActivity {

    Button bt_1;
    EditText ed_1;
    EditText ed_2;

    String[] Lista;
    final boolean[] verResultados = {false};
    final boolean[] gerar_numeros = {true};
    final boolean[] enviar = {false};



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
        


        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gerar_numeros[0]){
                    int[] val = valida(ed_1.getText().toString(),ed_2.getText().toString());
                    gerarNumeros(val[0], val[1]);
                    bt_1.setText("Enviar");
                }
                if(verResultados[0]){
                    print("Chamando uma nova tela > ");
                    Intent in = new Intent(_main.this, resultados.class);
                    startActivity(in);
                }

                if (enviar[0]){
                    new Cliente(Lista, _main.this).start();
                    verResultados[0] = true;
                    gerar_numeros[0] = false;
                    enviar[0] = false;
                    bt_1.setText("Ver resultados");
                    bt_1.setBackgroundResource(R.color.color_verde);
                }


            }
        });

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

            String Numeros = "";
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
                       gerar_numeros[0] = false;
                       enviar[0] = true;
                }
            });

            alerta.show();

        }


    }

    public void print(String m){
        Log.v("< ---- SORT ----> ", m);
    }



}
