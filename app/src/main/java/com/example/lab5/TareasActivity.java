package com.example.lab5;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

    String canal1 = "importanteDefault";
    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        createNotificationChannel();

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
            TextView bienvenido = findViewById(R.id.bienvenido);
            bienvenido.setText(String.format("Tareas de %s", codigo));
            bienvenido.setVisibility(View.VISIBLE);
            StringBuilder tareasString = new StringBuilder();
            for (Tarea tarea : tareasList) {
                tareasString.append("Título: ").append(tarea.getTitulo()).append("\n");
                tareasString.append("Descripción: ").append(tarea.getDescripcion()).append("\n");
                tareasString.append("Fecha: ").append(tarea.getFecha()).append("\n\n");
            }
            textView.setText(tareasString.toString());
        }actualizarNotificacion(tareasList.size());

    }



    //NOTIDICACION
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(canal1,
                    "Canal notificaciones default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificaciones con prioridad default");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            askPermission();
        }
    }
    private void askPermission() {
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(TareasActivity.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }
    private void actualizarNotificacion(int numeroTareas) {
        Intent intent = new Intent(this, TareasActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal1)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Tareas Registradas")
                .setContentText("Número de tareas: " + numeroTareas)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                // para la  persistente
                .setOngoing(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
}