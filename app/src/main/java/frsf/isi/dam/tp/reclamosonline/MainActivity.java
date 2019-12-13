package frsf.isi.dam.tp.reclamosonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuListareclamo:
                Intent i1 = new Intent(MainActivity.this, ListaReclamosActivity.class);
                startActivity(i1);
                return true;

            case R.id.mnuNuevoReclamo:
                Intent i = new Intent(MainActivity.this,FormReclamoActivity.class);
                startActivity(i);
                return true;

            case R.id.mnuMapaReclamos:
                Intent i2 = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i2);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
