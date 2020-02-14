package frsf.isi.dam.tp.reclamosonline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiDatabaseHelper extends SQLiteOpenHelper {

    private static MiDatabaseHelper _INSTANCIA;
    private static final String SQL_CREAR_RECLAMOS =
            "CREATE TABLE RECLAMOS (" +
                    " _id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " nombre TEXT," +
                    " estado TEXT," +
                    " tipo TEXT," +
                    " latitud REAL," +
                    " longitud REAL)";

    public static final String SQL_SELECT= "SELECT _id,nombre, telefono, correoelectronico, estado, tipo, latitud, longitud FROM RECLAMOS ";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reclamos.db";

    private MiDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR_RECLAMOS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE RECLAMOS ADD TELEFONO INTEGER");
        db.execSQL("ALTER TABLE RECLAMOS ADD CORREO_ELECTRONICO TEXT");
            }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public static MiDatabaseHelper getInstance(Context ctx){
        if(_INSTANCIA==null){
            _INSTANCIA = new MiDatabaseHelper(ctx);
        }
        return _INSTANCIA;
    }
}