package com.trairas.nig.sort;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.trairas.nig.sort.Util.OperArquivos;

public class resultados extends AppCompatActivity {

    OperArquivos opr = new OperArquivos();
    TableLayout tl;
    Button bt_1;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addTitleTabela(String left, String rigth){
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);


        TextView tv_left = new TextView(this);
        tv_left.setGravity(Gravity.LEFT);
        tv_left.setBackgroundColor(Color.GRAY);
        tv_left.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv_left.setText(left);

        TextView tv_rigth = new TextView(this);
        tv_rigth.setGravity(Gravity.RIGHT);
        tv_rigth.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv_rigth.setBackgroundColor(Color.GRAY);
        tv_rigth.setText(rigth);

        tr.addView(tv_left);
        tr.addView(tv_rigth);
        tl.addView(tr);
    }

    private void addConteudoTabela(String left, String rigth){

        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);


        TextView tv_left = new TextView(this);
        tv_left.setGravity(Gravity.LEFT);
        tv_left.setBackgroundColor(Color.WHITE);
        tv_left.setText("   "+left+"  ");

        TextView tv_rigth = new TextView(this);
        tv_rigth.setGravity(Gravity.RIGHT);
        tv_rigth.setBackgroundColor(Color.WHITE);
        tv_rigth.setText("   "+rigth+"  ");

        tr.addView(tv_left);
        tr.addView(tv_rigth);
        tl.addView(tr);
    }

    private String[] getMetricas(String lista){
        String[] v = new String[50];

        int contador = 0;
        String tmp = "";
        boolean inserir = false;

        for(int i=0;i<lista.length();i++){
            if (lista.charAt(i)=='['){
                inserir = true;
            }
            if(lista.charAt(i)==']'){
                v[contador] = tmp;
                inserir = false;
                tmp = "";
                contador +=1;
            }
            else if(inserir){
                if(lista.charAt(i) != '[' && lista.charAt(i) != ']'){
                    tmp += lista.charAt(i);
                }

            }

        }

        return v;
    }

    private void exibirNumerosOrdenados(){

        String Numeros = opr.ler(resultados.this, "ordenada.txt");
        print("Numeros Ordenados = "+Numeros);

        final AlertDialog.Builder alerta = new AlertDialog.Builder(resultados.this);
        alerta.setTitle("Números Ordenados");
        alerta.setMessage(Numeros);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alerta.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resultados);

       bt_1 = (Button) findViewById(R.id.res_bt_1);


        tl = (TableLayout) findViewById(R.id.tabela);
        addTitleTabela(" SHELL "," QUICKSORT ");

        String lista_qs = opr.ler(resultados.this, "qs.txt");
        String lista_ss = opr.ler(resultados.this, "ss.txt");

        String[] v_qs;
        String[] v_ss;

        print("vendo os dados de QS > \n"+lista_qs);

        v_qs = getMetricas(lista_qs);
        v_ss = getMetricas(lista_ss);

        for(int i=0;i<50;i++){
           addConteudoTabela(v_qs[i],v_ss[i]);
        }

        print("\nvendo os dados de SS > \n"+lista_ss);


          bt_1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  exibirNumerosOrdenados();
              }
          });


    }


    /**-----------------------------------------------------------------*/


    private void print(String m){
        Log.v("< -- Resultados--->", m);
    }

}
