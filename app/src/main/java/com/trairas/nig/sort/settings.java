package com.trairas.nig.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trairas.nig.sort.Util.OperArquivos;

public class settings extends AppCompatActivity {

    EditText ed_1;
    EditText ed_2;
    Button bt_1;
    String FILE_IP = "_ip.txt";
    String FILE_PORTA = "_porta.txt";

    OperArquivos opr = new OperArquivos();


    private boolean val_char(char letra, String val){
        for (int i=0;i<val.length();i++){
            if(letra == val.charAt(i)){
                return true;
            }
        }
        return false;
    }

    private boolean val_string(String src, String val){
        for(int i=0;i<src.length();i++){
            if(!val_char(src.charAt(i), val)){
                return false;
            }
        }
        return true;
    }

    private void salveIP(String ip){
        opr.salvar(ip, settings.this, FILE_IP);
    }

    private void salveporta(String porta){
        opr.salvar(porta, settings.this, FILE_PORTA);
    }

    private void salvaCampos(String ip, String porta){
        if(ip.length() > 0 && porta.length() >0){
            if (val_string(ip, "1234567890.") && val_string(porta, "123456789")){
                salveIP(ip);
                salveporta(porta);
                Toast.makeText(settings.this, "Dados Salvos Com sucesso", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(settings.this, "Dados inválidos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getIP(){

        String ip;

        try{
            ip = opr.ler(settings.this, FILE_IP);
        }catch (Exception erro){
            print("Erro ao obter dados do arquivo "+FILE_IP);
            ip = "404";
        }

        return ip;}

    private String getporta(){

        String porta;

        try{
            porta = opr.ler(settings.this, FILE_PORTA);
        }catch (Exception erro){
            print("Erro ao obter dados do arquivo "+FILE_PORTA);
            porta = "Porta dos Fundos";
        }

        return porta;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_settings);

        ed_1 = (EditText) findViewById(R.id.cf_ed_1);
        ed_1.setText(getIP());
        ed_1.setInputType(InputType.TYPE_CLASS_PHONE);
        ed_2 = (EditText) findViewById(R.id.cf_ed_2);
        ed_2.setText(getporta());
        ed_2.setInputType(InputType.TYPE_CLASS_PHONE);

        bt_1 = (Button) findViewById(R.id.cf_bt_1);
        bt_1.setText("Alterar");
        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaCampos(ed_1.getText().toString(), ed_2.getText().toString());
            }
        });

    }



    private void print(String m){
        Log.v("<--- Settings--> ", m);
    }
}
