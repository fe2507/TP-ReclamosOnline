package frsf.isi.dam.tp.reclamosonline;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.tp.reclamosonline.modelo.Reclamo;

public class ListaReclamosActivity extends AppCompatActivity {

    List<Reclamo> listaReclamos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tareas);
        cargarLista();
        Log.d("TP_DEBUG","cargar la lista");
        ListaReclamoAdapter miAdapter = new ListaReclamoAdapter(this,listaReclamos);
        ListView lv = (ListView) findViewById(R.id.listReclamos);
        lv.setAdapter(miAdapter);
    }

    private void cargarLista(){
        SQLiteDatabase db = MiDatabaseHelper.getInstance(this).getReadableDatabase();
        Cursor resultado = db.rawQuery(MiDatabaseHelper.SQL_SELECT,null);
        while (resultado.moveToNext()){
            Reclamo aux = new Reclamo(resultado.getInt(0),
                    resultado.getString(1),
                    resultado.getString(2),
                    resultado.getString(3),
                    resultado.getDouble(4),
                    resultado.getDouble(5)
                    );
            listaReclamos.add(aux);
        }
    }
}
