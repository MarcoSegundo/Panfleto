package br.bcc.middleware.panfleto;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class MainActivity extends AppCompatActivity {

    private ListView listPanfletos;

    private ArrayAdapter<String> adpListPanf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listPanfletos = (ListView) findViewById(R.id.listPanfletos);

        adpListPanf = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        //new Task().execute("");

        conectaList();

        /*adpListPanf.add("Extra");
        adpListPanf.add("Casas Bahia");
        adpListPanf.add("Saraiva");*/
        listPanfletos.setAdapter(adpListPanf);

        listPanfletos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String panfleto = (String)parent.getItemAtPosition(position);
                String detalhes = conectaDetails(panfleto);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("Panfleto",detalhes);
                startActivity(intent);
            }
        });
    }

    public void conectaList() {
        Comm mCliente = new Comm(new Endereco("192.168.43.135", "5000"));

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

            System.out.println(m);
            JSONArray n = (JSONArray) jp.parse((String) m.get("result"));
            JSONObject nomeServer = (JSONObject) n.get(0);

            for (int i=0;i<n.size();i++) {
                JSONObject jo = (JSONObject) n.get(i);
                String loja = (String) jo.get("nome");
                adpListPanf.add(loja);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String conectaDetails(String loja){
        Comm mCliente = new Comm(new Endereco("192.168.43.135", "5000"));

        JSONParser jp = new JSONParser();

        String retorno = "";

        try {
            JSONObject response = new JSONObject();
            response.put("op", "retornarLoja");
            JSONObject data = new JSONObject();
            data.put("nomeServico", loja);
            response.put("data", data);

            String enderecoServidor = mCliente.requestAndReceive(response
                    .toJSONString());

            System.out.println(enderecoServidor);

            JSONObject endereco  = (JSONObject) jp.parse(enderecoServidor);

            JSONObject obj =  (JSONObject) jp.parse((String) endereco.get("result"));


            String porta = (String) obj.get("porta");
            Comm mCliente2 = new Comm(new Endereco("192.168.43.135", porta));

            JSONObject response2 = new JSONObject();
            response2.put("op", "retornarPanfletos");

            retorno = mCliente2.requestAndReceive(response2.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    class Task extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            System.out.println("tol");

            return null;
        }

        protected void onPostExecute(String file_url) {

        }
    }
}
