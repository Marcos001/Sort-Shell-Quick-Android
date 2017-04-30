package com.trairas.nig.sort.Util;

import android.content.Context;
import android.util.Log;


/**
 * Created by nig on 02/04/17.
 */

public class Util {

    OperArquivos op;


    public void util(){

    }

    public boolean valida_um_carater(char c, String letrasValidas){

        boolean encontrou = false;

        for(int i=0;i<letrasValidas.length();i++){
            if(c == letrasValidas.charAt(i)){
                encontrou = true;
            }
        }

        return encontrou;
    }

    public boolean validarCaracters(String entrada, String valida){

        boolean ver_palavra = true;

        //verificando se os caracters sao validos

        for(int i=0;i<entrada.length();i++){

            if(!valida_um_carater(entrada.charAt(i),valida)){
                print("entrada invalida.");
                ver_palavra = false;
            }
        }
        return ver_palavra;
    }

    public boolean verificar_se_ja_tem(String palavra, Context c, String FILE){

        boolean encontrado = false;

        op  = new OperArquivos();

        String[] palavras = op.Todas_palavras(op.ler(c, FILE));

        for(int i=0;i< palavras.length;i++){
            if(palavra.equals(palavras[i])){
                encontrado = true;
            }
        }

        return encontrado;
    }

    public String[] getIp(String[] palavras){

        final String[] nome = new String[palavras.length];
        final String[] ips = new String[palavras.length];

        for (int i=0;i<palavras.length;i++){

            boolean found = false;
            String tmp_nome = "";
            String tmp_ips = "";

            for(int j=0; j< palavras[i].length();j++){

                if(palavras[i].charAt(j) == ':'){
                    found = true;
                    j+=1;
                }

                if (!found){
                    tmp_nome += palavras[i].charAt(j);
                }

                if (found){
                    tmp_ips += palavras[i].charAt(j);
                }

            }
            nome[i] = tmp_nome;
            ips[i] = tmp_ips;

        }

        return nome;
    }

    public String[] getNome(String[] palavras){
        final String[] nome = new String[palavras.length];
        final String[] ips = new String[palavras.length];

        for (int i=0;i<palavras.length;i++){

            boolean found = false;
            String tmp_nome = "";
            String tmp_ips = "";

            for(int j=0; j< palavras[i].length();j++){

                if(palavras[i].charAt(j) == ':'){
                    found = true;
                    j+=1;
                }

                if (!found){
                    tmp_nome += palavras[i].charAt(j);
                }

                if (found){
                    tmp_ips += palavras[i].charAt(j);
                }

            }
            nome[i] = tmp_nome;
            ips[i] = tmp_ips;

        }


        return ips;
    }



    public void print(String message){
        Log.v(".--------> ",message);
    }

}
