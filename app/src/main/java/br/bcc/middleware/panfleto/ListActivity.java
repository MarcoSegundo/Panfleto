package br.bcc.middleware.panfleto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Set;

public class ListActivity extends AppCompatActivity {

    private ListView listPanfletos;

    private ArrayAdapter<String> adpListPanf;

    HashMap<String, String> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listPanfletos = (ListView) findViewById(R.id.listPanfleto);

        adpListPanf = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        Intent intent = getIntent();

        if(intent.hasExtra("Panfletos")){
            details = (HashMap<String, String>) intent.getSerializableExtra("Panfletos");
            Set<String> lista = details.keySet();
            for (String j : lista){
                adpListPanf.add(j);
            }
        }

        listPanfletos.setAdapter(adpListPanf);

        listPanfletos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String panfleto = (String) parent.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this,panfleto,Toast.LENGTH_SHORT).show();
                String texto = details.get(panfleto);

                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                intent.putExtra("Panfleto", texto);
                startActivity(intent);
            }
        });
    }
}
