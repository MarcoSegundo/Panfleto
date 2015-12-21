package br.bcc.middleware.panfleto;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class IpActivity extends AppCompatActivity {

    private EditText ipText;
    private EditText portaText;
    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        ipText = (EditText) findViewById(R.id.ipText);
        portaText = (EditText) findViewById(R.id.portaText);
        btnOK = (Button)findViewById(R.id.idOk);

        btnOK.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validate(ipText.getText().toString())){
                    Intent mainActivity = new Intent(IpActivity.this, MainActivity.class);
                    mainActivity.putExtra("IP", ipText.getText().toString());
                    mainActivity.putExtra("Porta", portaText.getText().toString());
                    startActivity(mainActivity);
                 } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(IpActivity.this);
                    dlg.setMessage("Digite um IP válido!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
            }
        });
    }

    private  static  final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$" );

    public  static  boolean validate ( final  String ip )  {
        return PATTERN.matcher(ip).matches();
    }
}
