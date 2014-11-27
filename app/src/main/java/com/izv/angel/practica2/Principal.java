package com.izv.angel.practica2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;


public class Principal extends Activity {

    private EditText et1;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        et1= (EditText) findViewById(R.id.etTexto);
        Intent intent = getIntent();
        Uri data = intent.getData();
        path=data.getPath();
        if(intent.getType().equals("text/plain")) {
            leerArchivo(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void leerArchivo(Uri data) {
        try {
            InputStream in = getContentResolver().openInputStream(data);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String linea = "";
            StringBuilder texto = new StringBuilder();
            while ( (linea = br.readLine()) != null ) {
                texto.append(linea+"\n");
            }
            br.close();
            et1.setText(texto.toString());
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

    public void guardarArchivo(View v){
        String texto = et1.getText().toString();
        try {
            File archivo = new File(path);
            OutputStreamWriter escribir = new OutputStreamWriter(new FileOutputStream(archivo));
            escribir.write(texto);
            escribir.flush();
            escribir.close();
            Toast.makeText(this, R.string.guardar, Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            File archivoAux = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            guardarDcim(texto);
        }

    }

    public void guardarDcim( String texto){
        File archivo=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),path.toString().replace("/","")+".txt");
        try {
            OutputStreamWriter escribir = new OutputStreamWriter(new FileOutputStream(archivo));
            escribir.write(texto);
            escribir.flush();
            escribir.close();
            Toast.makeText(this, R.string.guardarExt, Toast.LENGTH_SHORT).show();
        } catch(IOException e){

        }
    }


}
