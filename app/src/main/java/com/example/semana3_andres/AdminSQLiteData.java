package com.example.semana3_andres;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bd_sistema";
    private static final int DATABASE_VERSION = 1;

    public AdminSQLiteData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TABLA CLIENTE
        db.execSQL("CREATE TABLE cliente (dni TEXT PRIMARY KEY, nombre TEXT, apellido TEXT, telefono TEXT, email TEXT, direccion TEXT)");

        // TABLA SERVICIO - NUEVO DE LA FOTO 3
        db.execSQL("CREATE TABLE servicio (id_servicio TEXT PRIMARY KEY, cliente TEXT, nombre_servicio TEXT, descripcion TEXT, precio_base TEXT, estado TEXT)");

        // TABLA MANTENIMIENTO - PARA REPORTE
        db.execSQL("CREATE TABLE mantenimiento (codigo INTEGER PRIMARY KEY AUTOINCREMENT, id_servicio TEXT, cliente TEXT, tipo_mantenimiento TEXT, descripcion TEXT, precio_base TEXT, estado TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cliente");
        db.execSQL("DROP TABLE IF EXISTS servicio");
        db.execSQL("DROP TABLE IF EXISTS mantenimiento");
        onCreate(db);
    }

    // ===== MÉTODOS CLIENTE =====
    public boolean insertarCliente(String dni, String nombre, String apellido, String telefono, String email, String direccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("dni", dni); valores.put("nombre", nombre); valores.put("apellido", apellido);
        valores.put("telefono", telefono); valores.put("email", email); valores.put("direccion", direccion);
        long resultado = db.insert("cliente", null, valores);
        db.close();
        return resultado != -1;
    }

    public Cursor buscarCliente(String dni) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cliente WHERE dni = ?", new String[]{dni});
    }

    public boolean modificarCliente(String dni, String nombre, String apellido, String telefono, String email, String direccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre); valores.put("apellido", apellido);
        valores.put("telefono", telefono); valores.put("email", email); valores.put("direccion", direccion);
        int resultado = db.update("cliente", valores, "dni = ?", new String[]{dni});
        db.close();
        return resultado > 0;
    }

    public boolean eliminarCliente(String dni) {
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete("cliente", "dni = ?", new String[]{dni});
        db.close();
        return resultado > 0;
    }

    // ===== MÉTODOS SERVICIO - FOTO 3 =====
    public boolean insertarServicio(String id, String cliente, String nombre, String desc, String precio, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id_servicio", id); valores.put("cliente", cliente); valores.put("nombre_servicio", nombre);
        valores.put("descripcion", desc); valores.put("precio_base", precio); valores.put("estado", estado);
        long resultado = db.insert("servicio", null, valores);
        db.close();
        return resultado != -1;
    }

    public Cursor buscarServicio(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM servicio WHERE id_servicio = ?", new String[]{id});
    }

    public boolean modificarServicio(String id, String cliente, String nombre, String desc, String precio, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cliente", cliente); valores.put("nombre_servicio", nombre);
        valores.put("descripcion", desc); valores.put("precio_base", precio); valores.put("estado", estado);
        int resultado = db.update("servicio", valores, "id_servicio = ?", new String[]{id});
        db.close();
        return resultado > 0;
    }

    // ===== MÉTODOS REPORTE =====
    public Cursor listarMantenimientos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM mantenimiento ORDER BY codigo DESC", null);
    }
}