package com.catata.transmisionsender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //El receptor ha de esperar por esta acción (coincidir el String)
    private static final String ACTION_RECEIVER = MainActivity.class.getCanonicalName() + ".ACTION_RECEIVER"; //com.catata.transmisionsender.MainActivity.ACTION_RECEIVER

    private static final String EXTRA_DATA = MainActivity.class.getCanonicalName() + ".EXTRA_DATA";
    private static final int WAITING_TIME = 5000;

    EditText etNombre, etCantidad;

    ArrayList<Producto> listProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText)findViewById(R.id.etNombre);
        etCantidad = (EditText)findViewById(R.id.etCantidad);

        listProductos = new ArrayList<Producto>();
    }

    public void addProducto(View view) {
        String nombre = etNombre.getText().toString();
        int cantidad = Integer.parseInt(etCantidad.getText().toString());

        listProductos.add(new Producto(nombre, cantidad));

        Toast.makeText(this, listProductos.size() + " productos añadidos", Toast.LENGTH_SHORT).show();
    }

    public void enviarBroadcast(View view) {
        Intent i = new Intent(ACTION_RECEIVER);
        //Metemos el Array de productos como extra en el intent
        i.putParcelableArrayListExtra(EXTRA_DATA, listProductos);

        //Hacemos que no lo envíe de inmediato, esperamos 5 segundos para poder cambiar de aplicación y verlo
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(i);
            }
        },  WAITING_TIME);

    }
}