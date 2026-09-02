package com.example.semana3_andres;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etCodigo, etNombre, etCargo, etSueldo;
    Button btnRegistrar, btnBuscar, btnModificar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CONECTAR LOS CAMPOS
        etCodigo = findViewById(R.id.etCodigo);
        etNombre = findViewById(R.id.etNombre);
        etCargo = findViewById(R.id.etCargo);
        etSueldo = findViewById(R.id.etSueldo);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // FOTO 10: LOS 4 BOTONES
        btnRegistrar.setOnClickListener(v -> registrarPersonal());
        btnBuscar.setOnClickListener(v -> buscarPersonal());
        btnModificar.setOnClickListener(v -> modificarPersonal());
        btnEliminar.setOnClickListener(v -> eliminarPersonal());
    }

    public void registrarPersonal() {
        AdminSQLiteData admin = new AdminSQLiteData(this);
        boolean ok = admin.insertar(etNombre.getText().toString(), etCargo.getText().toString(), etSueldo.getText().toString());
        if(ok){
            Toast.makeText(this, "PERSONAL REGISTRADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
            limpiar();
        }else{
            Toast.makeText(this, "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarPersonal() {
        AdminSQLiteData admin = new AdminSQLiteData(this);
        Cursor cursor = admin.buscar(etCodigo.getText().toString());
        if(cursor.moveToFirst()){
            etNombre.setText(cursor.getString(0));
            etCargo.setText(cursor.getString(1));
            etSueldo.setText(cursor.getString(2));
            Toast.makeText(this, "PERSONAL ENCONTRADO", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NO EXISTE PERSONAL CON ESE CODIGO", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    public void modificarPersonal() {
        AdminSQLiteData admin = new AdminSQLiteData(this);
        boolean ok = admin.modificar(etCodigo.getText().toString(), etNombre.getText().toString(), etCargo.getText().toString(), etSueldo.getText().toString());
        if(ok){
            Toast.makeText(this, "PERSONAL MODIFICADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NO EXISTE PERSONAL", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarPersonal() {
        AdminSQLiteData admin = new AdminSQLiteData(this);
        boolean ok = admin.eliminar(etCodigo.getText().toString());
        if(ok){
            Toast.makeText(this, "PERSONAL ELIMINADO", Toast.LENGTH_SHORT).show();
            limpiar();
        }else{
            Toast.makeText(this, "NO EXISTE PERSONAL", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiar(){
        etCodigo.setText("");
        etNombre.setText("");
        etCargo.setText("");
        etSueldo.setText("");
    }
}