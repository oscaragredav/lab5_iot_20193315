package com.example.lab5;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab5.dto.Tarea;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class GuardarActivity extends AppCompatActivity {

    String fecha;
    String codigo;
    String titulo;
    Tarea tarea = new Tarea();
    TextView tvFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar);

        codigo = getIntent().getStringExtra("codigoPUCP");
        EditText edTitulo = findViewById(R.id.edTitulo);
        EditText edDdescripcion = findViewById(R.id.etDescripcion);
        titulo = edTitulo.getText().toString();
        tvFecha = findViewById(R.id.tvFecha);

        //lo obtenido mediante los edittext, el calendario y el codigo madnado se guarda en un objeto para luego
        //guardarse como JSON
        tarea.setCodigo(codigo);
        tarea.setTitulo(titulo);
        tarea.setDescripcion(edDdescripcion.getText().toString());
        tarea.setFecha(fecha);

        Button btnGuardar = findViewById(R.id.guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = edTitulo.getText().toString().trim();
                String descripcion = edDdescripcion.getText().toString().trim();
                if (titulo.isEmpty() || descripcion.isEmpty() || fecha == null) {
                    Toast.makeText(GuardarActivity.this, "Ingrese título, descripción y fecha", Toast.LENGTH_SHORT).show();
                    return;
                }

                tarea = new Tarea();
                tarea.setCodigo(codigo);
                tarea.setTitulo(titulo);
                tarea.setDescripcion(edDdescripcion.getText().toString());
                tarea.setFecha(fecha);

                Toast.makeText(GuardarActivity.this, "Tarea guardada correctamente", Toast.LENGTH_SHORT).show();

                guardar(tarea);
                Intent intent = new Intent(GuardarActivity.this, TareasActivity.class);
                intent.putExtra("codigoPUCP", codigo);
                startActivity(intent);
            }
        });

    }

    public void abrirCalendario (View view){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONDAY);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(GuardarActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha = dayOfMonth + "/" + (month+1) + "/" + year;
                tvFecha.setText(fecha);
                tvFecha.setVisibility(View.VISIBLE);
            }
        }, ano, mes, dia);
        datePickerDialog.show();
    }

    public void guardar (Tarea tarea) {
        SharedPreferences sharedPreferences = getSharedPreferences("tareas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonTareas = sharedPreferences.getString(codigo + "_tareas", "[]");
        Type type = new TypeToken<ArrayList<Tarea>>() {
        }.getType();
        ArrayList<Tarea> tareasList = gson.fromJson(jsonTareas, type);
        tareasList.add(tarea);

        jsonTareas = gson.toJson(tareasList);
        editor.putString(codigo + "_tareas", jsonTareas);
        editor.apply();
    }
}