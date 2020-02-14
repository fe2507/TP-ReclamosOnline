package frsf.isi.dam.tp.reclamosonline;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.tp.reclamosonline.modelo.Reclamo;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private boolean mostrarUnReclamo = false;
    private Reclamo reclamoActual;
    private List<Reclamo> listaReclamos = new ArrayList<>();
    private static final Integer CARGAR_LISTA_RECLAMOS = 100;
    private float hue=0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d("TP_DEBUG", "mapa: " + this.getIntent());
        if (this.getIntent() != null) {
            reclamoActual = (Reclamo) this.getIntent().getSerializableExtra("reclamo");
            this.mostrarUnReclamo = (Boolean) this.getIntent().getBooleanExtra("mostrarReclamo", false);
            Log.d("TP_DEBUG", "datos: " + reclamoActual + "- " + mostrarUnReclamo);

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        if (mostrarUnReclamo) {
            mostrarReclamo();
        } else {
            cargarLista();
        }
    }

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message inputMessage) {
            // Gets the image task from the incoming Message object.
            Log.d("TP_DEBUG","coordenada seleccionada : "+inputMessage.what);
            if(inputMessage.what==MapsActivity.CARGAR_LISTA_RECLAMOS){
                mostrarListaEnMapa();
            }
        }
    };

    private void mostrarListaEnMapa() {
        Double maxN= Double.MIN_VALUE,maxE= Double.MIN_VALUE;
        Double minS= Double.MAX_VALUE,minO = Double.MAX_VALUE;
        for(Reclamo r : this.listaReclamos){
            if(r.getLatitud()<minS) minS = r.getLatitud();
            if(r.getLatitud()> maxN) maxN= r.getLatitud();
            if(r.getLongitud()<minO) minO = r.getLongitud();
            if(r.getLongitud()>maxE) maxE= r.getLongitud();
            LatLng centro = new LatLng(r.getLatitud(),r.getLongitud());
            switch(r.getTipo()){
                case BACHE:hue=240.0F;
                case RESIDUOS:hue=120.0F;
                case TRANSITO:hue=30.0F;
                case TRANSPORTE:hue=0.0F;
                case ILUMNINACION:hue=60.0F;
                default:hue=90.0F;
                    break;
            }
            this.mMap.addMarker(new MarkerOptions()
                    .position(centro).icon(BitmapDescriptorFactory.defaultMarker(hue)).title(r.getNombre())
                    .snippet(r.getNombre()+ ":"+ r.getEstado()));
            this.mMap.addPolyline((new PolylineOptions()).add());
        }
        Log.d("TP_DEBUG", "limites: " + maxN+" _ "+maxE+" _ "+minS+" _ "+minO+" _ ");
        LatLng norEste= new LatLng(maxN,maxE);
        LatLng surOeste= new LatLng(minS,minO);
        LatLngBounds limites = new LatLngBounds(surOeste,norEste);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(limites,11));

    }



    private void cargarLista(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                MiDatabaseHelper dbHelper =MiDatabaseHelper.getInstance(MapsActivity.this);
                SQLiteDatabase db =dbHelper.getReadableDatabase();

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
                Log.d("TP_DEBUG","lista reclamos: "+listaReclamos);
                Message m = handler.obtainMessage();
                m.what=CARGAR_LISTA_RECLAMOS;
                handler.sendMessage(m);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void mostrarReclamo(){
        LatLng centro = new LatLng(this.reclamoActual.getLatitud(),this.reclamoActual.getLongitud());
        switch(reclamoActual.getTipo()){
            case BACHE:hue=240.0F;
            case RESIDUOS:hue=120.0F;
            case TRANSITO:hue=30.0F;
            case TRANSPORTE:hue=0.0F;
            case ILUMNINACION:hue=60.0F;
            default:hue=90.0F;
            break;
        }
        this.mMap.addMarker(new MarkerOptions()
                .position(centro).icon(BitmapDescriptorFactory.defaultMarker(hue)).title(this.reclamoActual.getNombre())
                .snippet(this.reclamoActual.getNombre()+ ":"+ this.reclamoActual.getEstado()));
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro,11));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d("TP_DEBUG","coordenada seleccionada : "+latLng);

        Intent intRes = new Intent();
        intRes.putExtra("coordenadas",latLng);
        setResult(RESULT_OK,intRes);
        Log.d("TP_DEBUG","finisih: "+ RESULT_OK);
        //finishActivity(FormReclamoActivity.BUSCAR_COORDENADAS);
        finish();
    }


}
