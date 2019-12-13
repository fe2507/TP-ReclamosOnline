package frsf.isi.dam.tp.reclamosonline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import frsf.isi.dam.tp.reclamosonline.modelo.Estado;
import frsf.isi.dam.tp.reclamosonline.modelo.Reclamo;
import frsf.isi.dam.tp.reclamosonline.modelo.TipoReclamo;

public class FormReclamoActivity extends AppCompatActivity {

    private String[] tiposReclamo = {"BACHE","ILUMNINACION","RESIDUOS","TRANSPORTE","TRANSITO","OTROS"};

    public static final int BUSCAR_COORDENADAS = 99;
    private Reclamo reclamo;
    private EditText tituloEdit;
    private Spinner tipoReclamoCmb;
    private TextView coordenadasTv;
    private Button buscarCoordBtn;
    private Button guardarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reclamo);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<String> tipoReclamoAdapter = new ArrayAdapter<>(FormReclamoActivity.this,android.R.layout.simple_spinner_item,tiposReclamo);
        tipoReclamoCmb = (Spinner) findViewById(R.id.recTipo);
        tipoReclamoCmb.setAdapter(tipoReclamoAdapter);
        tituloEdit = (EditText) findViewById(R.id.recDesc);
        coordenadasTv = (TextView) findViewById(R.id.coordSelec);
        if (this.getIntent() != null) {
            reclamo = (Reclamo) this.getIntent().getSerializableExtra("reclamo");
            Log.d("TP_DEBUG", "datos editar: " + reclamo );
            tituloEdit.setText(reclamo.getNombre());
            tipoReclamoCmb.setSelection(indiceTipoReclamo(reclamo.getTipo().toString()));
            coordenadasTv.setText(reclamo.getLatitud()+":"+reclamo.getLongitud());
        } else {
            reclamo = new Reclamo();
        }

        buscarCoordBtn = (Button) findViewById(R.id.btnBuscarCoord);
        guardarBtn = (Button) findViewById(R.id.btnGuardar);

        buscarCoordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(FormReclamoActivity.this,MapsActivity.class);
                // iniciar la actividad del mapa para obtener un resultado
                // y pasar como codigo de request el valor de BUSCAR_COORDENADAS
            }
        });

        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reclamo.setNombre(tituloEdit.getText().toString());
                if(tipoReclamoCmb.getSelectedItem()!=null){
                    reclamo.setTipo(TipoReclamo.valueOf(tipoReclamoCmb.getSelectedItem().toString()));
                }
                reclamo.setEstado(Estado.ENVIADO);
                GuardarReclamoAsyc tareaGuardar = new GuardarReclamoAsyc();
                tareaGuardar.execute(reclamo);
            }
        });
    }

    private int indiceTipoReclamo(String s){
        for(int i=0;i<tiposReclamo.length;i++){
            if(tiposReclamo[i].equalsIgnoreCase(s)) return i;
        }
        return -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TP_DEBUG",":"+resultCode);
        Log.d("TP_DEBUG",":"+requestCode);
        if(requestCode==BUSCAR_COORDENADAS && resultCode == RESULT_OK){
            // obtener de los extras de data, el parcelable con las coordenadas
            // y asignarlo a un objeto LatLng
            // luego al reclamo setearle la longitud y latitud con
            // (objeto LatLNG).latitude y (objeto LatLNG).longitude
            // finalmente setear las coordendas en el coordenadasTv
        }
    }

    private class GuardarReclamoAsyc extends AsyncTask<Reclamo,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Reclamo... reclamos) {
            Reclamo reclamo = reclamos[0];
            ContentValues cv = new ContentValues();
            if(reclamo!=null){
                cv.put("nombre",reclamo.getNombre());
                cv.put("estado",reclamo.getEstado().toString());
                cv.put("tipo",reclamo.getTipo().toString());
                cv.put("latitud",reclamo.getLatitud());
                cv.put("longitud",reclamo.getLongitud());
                SQLiteDatabase db = MiDatabaseHelper.getInstance(FormReclamoActivity.this).getWritableDatabase();
                if(reclamo.getId()!=null && reclamo.getId()>0){
                    String[] condicion= { ""+reclamo.getId() };
                    db.update("RECLAMOS",cv,"_id=?",condicion);
                } else {
                    db.insert("RECLAMOS","nombre",cv);
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(FormReclamoActivity.this,"TAREA GUARDADA OK!",Toast.LENGTH_LONG).show();
        }
    }
}
