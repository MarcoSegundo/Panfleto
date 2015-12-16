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
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONObject;

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

        /*new Task().execute("");

        conecta();*/

        adpListPanf.add("Extra");
        adpListPanf.add("Casas Bahia");
        adpListPanf.add("Saraiva");
        listPanfletos.setAdapter(adpListPanf);

        listPanfletos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String panfleto = (String)parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("Panfleto",panfleto);
                startActivity(intent);
            }
        });
    }

    public void conecta() {
        Comm mCliente = new Comm(new Endereco("192.168.25.10", "5000"));

        JSONObject response = new JSONObject();
        try {
            response.put("op", "retornarNomesServicos");
            JSONObject data = new JSONObject();
            data.put("nomeServico", "cea");
            response.put("data", data);
            String teste = mCliente.requestAndReceive(response.toString());

            System.out.println(teste);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Task extends AsyncTask<String, String, String> {
        private String url_server;


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
