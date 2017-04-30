package com.trairas.nig.sort;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.trairas.nig.sort.Util.OperArquivos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by nig on 28/04/17.
 */

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
        progress.show();

    }




    @Override
    public void run(){

        print("Iniciada a Thread");


        //iniciar um loop com um contador para barra de prograsso esperando uma mensagem do servidor
        OperArquivos opr = new OperArquivos();

        try{
            print("Iniciando conexão com o servidor > ");
            Socket socket = new Socket("10.0.0.102",12345);
            print("Comunicaão feita com sucesso!");

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
            print("Conexao fechada!");

            //deixa o botao verde pedindo pra chamar outra activity para ver os resultados

            progress.dismiss();



        }catch (Exception e){
            print("Erro ao iniciar socket cliente >");
        }

    }

    public void print(String m){
        Log.v("< ---- CLIENTE ----> ", m);
    }

}
