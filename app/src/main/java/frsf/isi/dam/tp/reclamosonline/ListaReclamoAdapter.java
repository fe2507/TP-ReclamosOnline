package frsf.isi.dam.tp.reclamosonline;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import frsf.isi.dam.tp.reclamosonline.modelo.Reclamo;

public class ListaReclamoAdapter extends ArrayAdapter<Reclamo> {

    Context context;
    List<Reclamo> listaReclamos;
    public ListaReclamoAdapter(Context ctx,List<Reclamo> l){
        super(ctx,0);
        this.context = ctx;
        Log.d("TP_DEBUG","recibo:"+l);
        listaReclamos = l;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View fila = convertView;
        if(fila==null){
            fila = LayoutInflater.from(context).inflate(R.layout.fila_reclamo, parent,false);
        }
        Reclamo r = this.listaReclamos.get(position);
        Log.d("TP_DEBUG","get view reclamo :"+r.toString());
        TextView tvTitulo = (TextView) fila.findViewById(R.id.tituloReclamoFila);
        TextView tvTipo= (TextView) fila.findViewById(R.id.tipoReclamoFila);
        Button btnVerEnMapa = (Button) fila.findViewById(R.id.btnVerEnMapa);
        btnVerEnMapa.setTag(r);

        Button btnEditar = (Button) fila.findViewById(R.id.btnEditar);
        btnEditar.setTag(r);
        tvTitulo.setText(r.getNombre());
        tvTipo.setText(r.getEstado().toString());
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reclamo aux = (Reclamo) v.getTag();
                Intent i = new Intent(context,FormReclamoActivity.class);
                i.putExtra("reclamo",aux);
                context.startActivity(i);
            }
        });

        btnVerEnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reclamo aux = (Reclamo) v.getTag();
                Intent i = new Intent(context,MapsActivity.class);
                i.putExtra("reclamo",aux);
                i.putExtra("mostrarReclamo",true);
                context.startActivity(i);
            }
        });

        return fila;
    }

    @Override
    public int getCount() {
        return this.listaReclamos.size();
    }
}
