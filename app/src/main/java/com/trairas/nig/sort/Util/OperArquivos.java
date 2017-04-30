package com.trairas.nig.sort.Util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_ENABLE_WRITE_AHEAD_LOGGING;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nig on 03/04/17.
 */

public class OperArquivos {

    Util u;

    public OperArquivos(){
        u = new Util();
    }

    public String[] Todas_palavras(String palavras_arquivo){


        int quantidade=0;

        for(int i=0;i < palavras_arquivo.length();i++){
            if(palavras_arquivo.charAt(i) == '\n'){
                quantidade +=1;
            }
        }

        String[] palavras = new String[quantidade];

        String tmp = "";
        int indice = 0;

        for(int i=0;i < palavras_arquivo.length();i++){

            if(palavras_arquivo.charAt(i) == '\n'){
                palavras[indice] = tmp;
                indice+=1;
                tmp = "";
            }
            else{
                tmp += palavras_arquivo.charAt(i);
            }
        }
        return palavras;
    }


    public String ler(Context contexto, String FILE){

        try{

            File arquivo_lido = contexto.getFileStreamPath(FILE);

            if (arquivo_lido.exists()){
                u.print("Arquivo existe");
                FileInputStream arquivo_ler = contexto.openFileInput(FILE);
                int tamanho =  arquivo_ler.available();
                byte dadosBytesLidos[] = new byte[tamanho];
                arquivo_ler.read(dadosBytesLidos);
                String dadosLidos = new String(dadosBytesLidos);


            return dadosLidos;
            }

            else{
                u.print("Arquivo nao existe.");
            }

        }catch (Exception erro){
            u.print("Erro ao Ler.");
        }
    return "Dimas\n";
    }


    public void salvar(String palavra, Context ctx, String FILE){

        if (ctx == null){
            u.print("Contexto nulo.");
            return;
        }

        u.print("word = "+palavra);

        try{
            
            FileOutputStream arquivo = ctx.openFileOutput(FILE, MODE_PRIVATE);
            arquivo.write(palavra.getBytes());
            arquivo.close();
            u.print("Arquivo Gravado");
        }

        catch (FileNotFoundException erro){
            u.print("Arquivo nao encontrado");
        }

        catch (Exception e) {
            u.print("Erro ao Gravar");
        }

    }

    public void deletar(Context ctx, String FILE){


        try{

            FileOutputStream arquivo = ctx.openFileOutput(FILE, MODE_PRIVATE);
            arquivo.write("".getBytes());
            arquivo.close();
            u.print("palavras deletadas");
        }

        catch (FileNotFoundException erro){
            u.print("Merda em deletar");
        }

        catch (Exception e) {
            u.print("Erro ao deletar");
        }

    }


    /**-------------------------------------------------------------*/






}

