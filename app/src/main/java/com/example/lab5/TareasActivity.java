package com.example.lab5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab5.dto.Tarea;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TareasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        String codigo = getIntent().getStringExtra("codigoPUCP");
        FloatingActionButton agregar = (FloatingActionButton) findViewById(R.id.agregar);


        agregar.setOnClickListener(v -> {
            Intent intent = new Intent(TareasActivity.this, GuardarActivity.class);
            intent.putExtra("codigoPUCP", codigo);
            startActivity(intent);
        });

        mostrarTareas(codigo);
    }

    private void mostrarTareas(String codigo) {
        SharedPreferences sharedPreferences = getSharedPreferences("tareas", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTareas = sharedPreferences.getString(codigo + "_tareas", "[]");
        Type type = new TypeToken<ArrayList<Tarea>>() {}.getType();
        ArrayList<Tarea> tareasList = gson.fromJson(jsonTareas, type);

        TextView textView = findViewById(R.id.textView);
        if (tareasList.isEmpty()) {
            textView.setText("No tiene tareas registradas"); 
        } else {
            StringBuilder tareasString = new StringBuilder();
            for (Tarea tarea : tareasList) {
                tareasString.append("Título: ").append(tarea.getTitulo()).append("\n");
                tareasString.append("Descripción: ").append(tarea.getDescripcion()).append("\n");
                tareasString.append("Fecha: ").append(tarea.getFecha()).append("\n\n");
            }
            textView.setText(tareasString.toString());
        }
    }
}