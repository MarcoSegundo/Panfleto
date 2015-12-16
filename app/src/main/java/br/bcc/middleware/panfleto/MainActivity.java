package br.bcc.middleware.panfleto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listPanfletos;

    private ArrayAdapter<String> adpListPanf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPanfletos = (ListView) findViewById(R.id.listPanfletos);

        adpListPanf = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adpListPanf.add("Extra");
        adpListPanf.add("Casas Bahia");
        adpListPanf.add("Saraiva");
        listPanfletos.setAdapter(adpListPanf);

        listPanfletos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String panfleto = (String)parent.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this,panfleto,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("Panfleto",panfleto);
                startActivity(intent);
            }
        });
    }
}
