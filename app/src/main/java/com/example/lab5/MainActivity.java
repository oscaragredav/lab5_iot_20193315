package com.example.lab5;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etCodigo;
    Button acceder;
    String canal1 = "importanteDefault";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        createNotificationChannel();

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


//    private void createNotificationChannel() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(canal1,
//                    "Canal notificaciones default",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("Canal para notificaciones con prioridad default");
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            askPermission();
//        }
//    }
//    private void askPermission() {
//        //android.os.Build.VERSION_CODES.TIRAMISU == 33
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
//                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
//                        PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{POST_NOTIFICATIONS},
//                    101);
//        }
//    }
//    public void notificarImportanceDefault(){
//        //Crear notificación
//        //Agregar información a la notificación que luego sea enviada a la actividad que se abre
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("pid","4616");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//        //
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal1)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("Mi primera notificación")
//                .setContentText("Esta es mi primera notificación en Android :D")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//        Notification notification = builder.build();
//        //Lanzar notificación
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//            notificationManager.notify(1, notification);
//        }
//
//    }
}