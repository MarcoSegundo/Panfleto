package br.bcc.middleware.panfleto;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView listLojas;

    private ArrayAdapter<String> adpListLoj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listLojas = (ListView) findViewById(R.id.listLojas);

        adpListLoj = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        conectaList();

        listLojas.setAdapter(adpListLoj);

        listLojas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String panfleto = (String)parent.getItemAtPosition(position);
                HashMap<String,String> detalhes = conectaDetails(panfleto);
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("Panfletos", detalhes);
                startActivity(intent);
            }
        });
    }

    public void conectaList() {
        Comm mCliente = new Comm(new Endereco("192.168.25.13", "5000"));

        JSONObject response = new JSONObject();
        response.put("op", "retornarNomesServicos");
        JSONObject data = new JSONObject();

        response.put("data", data);

        // passa valores no json
        // System.out.println(response.toJSONString());

        JSONParser jp = new JSONParser();

        JSONObject m = null;
        try {
            m = (JSONObject) jp.parse(mCliente
                    .requestAndReceive(response.toJSONString()));

            //System.out.println(m);
            JSONArray n = (JSONArray) jp.parse((String) m.get("result"));

            for (int i=0;i<n.size();i++) {
                JSONObject jo = (JSONObject) n.get(i);
                String loja = (String) jo.get("nome");
                adpListLoj.add(loja);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String,String> conectaDetails(String loja){
        Comm mCliente = new Comm(new Endereco("192.168.25.13", "5000"));

        JSONParser jp = new JSONParser();

        HashMap<String,String> retorno = new HashMap<String,String>();

        try {
            JSONObject response = new JSONObject();
            response.put("op", "retornarLoja");
            JSONObject data = new JSONObject();
            data.put("nomeServico", loja);
            response.put("data", data);

            String enderecoServidor = mCliente.requestAndReceive(response
                    .toJSONString());

            //System.out.println(enderecoServidor);

            JSONObject endereco  = (JSONObject) jp.parse(enderecoServidor);

            JSONObject obj =  (JSONObject) jp.parse((String) endereco.get("result"));


            String porta = (String) obj.get("porta");
            Comm mCliente2 = new Comm(new Endereco("192.168.25.13", porta));

            JSONObject response2 = new JSONObject();
            response2.put("op", "retornarPanfletos");

            JSONObject resp = (JSONObject) jp.parse(mCliente2.requestAndReceive(response2.toJSONString()));

            JSONArray n = (JSONArray) jp.parse((String) resp.get("result"));

            for (int i=0;i<n.size();i++) {
                JSONObject jo = (JSONObject) n.get(i);
                String panfle = (String) jo.get("titulo");
                String descPanfle = (String) jo.get("texto");
                retorno.put(panfle, descPanfle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
