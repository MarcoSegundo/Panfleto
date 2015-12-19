package br.bcc.middleware.panfleto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private TextView textDetailsPanfleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textDetailsPanfleto = (TextView)findViewById(R.id.textDetailsPanfleto);

        Bundle bundle = getIntent().getExtras();

        if(bundle.containsKey("Panfleto")) {
            String details = bundle.getString("Panfleto");
            textDetailsPanfleto.setText(details);
        }
    }
}
