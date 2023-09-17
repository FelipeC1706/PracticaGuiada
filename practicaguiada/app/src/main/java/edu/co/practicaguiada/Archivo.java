package edu.co.practicaguiada;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Archivo {
    private Context context;
    private Activity activity;

    public Archivo(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }
    private void crearDir(File file){
        if(!file.exists()) file.mkdirs();
    }
    public void saveFile(String nameFile, String contentFile){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            int writePermission = ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        File directory;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"comunidadUE");
        } else {
            directory = new File(Environment.getExternalStorageDirectory(),"comunidadUE");
        }

        crearDir(directory);
        Toast.makeText(context, "Ruta:" + directory, Toast.LENGTH_LONG).show();

        File file = new File(directory, nameFile);

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(contentFile);
            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(context, "El archivo ha sido guardado con éxito: " + nameFile, Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(context, "Error al crear el archivo: "+ nameFile, Toast.LENGTH_LONG).show();
        }
    }
}
