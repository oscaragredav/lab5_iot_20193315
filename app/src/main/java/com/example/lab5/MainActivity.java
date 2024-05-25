package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etCodigo;
    Button acceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acceder = findViewById(R.id.acceder);
        etCodigo = findViewById(R.id.etCodigo);

        acceder.setOnClickListener(v -> {
            String codigo = etCodigo.getText().toString().trim();
            if (codigo.isEmpty()) {
                Toast.makeText(MainActivity.this, "Ingrese su código PUCP", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Intent intent = new Intent(MainActivity.this, TareasActivity.class);
                intent.putExtra("codigoPUCP", codigo);
                startActivity(intent);
            }
        });
    }
}