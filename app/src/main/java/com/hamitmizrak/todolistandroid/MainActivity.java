package com.hamitmizrak.todolistandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

// Global Variable
private Button buttonTodolistRedirect;

// ONCREATE(1)
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    });

    // Loglama
    Log.e("onCreate","1.alan Create(Oluşturuldu)");

    // Toast
    //Toast.makeText(this, "Anasayfaya Hoşgeldiniz", Toast.LENGTH_SHORT).show();
    String welcome=getString(R.string.homepage);
    Toast.makeText(this, welcome, Toast.LENGTH_SHORT).show();

    // ID
    buttonTodolistRedirect =findViewById(R.id.notebook_main_redirect_id);
    // Java Tarafından Nasıl Background değiştirebilirim.
    buttonTodolistRedirect.setBackgroundColor(Color.parseColor("#FF5722"));
    buttonTodolistRedirect.setAlpha(0.8f);
    buttonTodolistRedirect.setOnClickListener(new View.OnClickListener() { //interface
        @Override
        public void onClick(View view) {
            // Confirm AlertDialog
            AlertDialog.Builder alertDialogTodoList= new AlertDialog.Builder(MainActivity.this);

            // Yanıt Evetse
            alertDialogTodoList.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Yönlendirme
                    Intent todoListIntent= new Intent(getApplicationContext(),TodoActivity.class);
                    Toast.makeText(MainActivity.this, "TodoList Sayfasına Yönlendirildi", Toast.LENGTH_SHORT).show();
                    // Bir Activityden başka bir Activity'e veri göndermek
                    String fullName="Hamit Mızrak";
                    todoListIntent.putExtra("key_main",fullName);
                    // Activity çalışsın
                    startActivity(todoListIntent);
                }
            });

            //Yanıt Hayırsa
            alertDialogTodoList.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "TodoList sayfasına yönlendirme yapılmadı", Toast.LENGTH_SHORT).show();

                }
            });

            // AlertDialog Bilgileri
            alertDialogTodoList.setTitle("TodoList Sayfası");
            alertDialogTodoList.setMessage("Application Yönlendirmesi");
            alertDialogTodoList.show();
        } // AlertDialog onClick
    }); //end Onclick buttonTodoListRedirect
} //end onCreate

// ONSTART(2)
@Override
protected void onStart() {
    super.onStart();
    Log.e("onStart","2.alan Start(başladı)");
}

// ONRESUME(3)
@Override
protected void onResume() {
    super.onResume();
    Log.e("onResume","3.alan Resume(Devam Ediyor)");
}

// ONPAUSE(4)
@Override
protected void onPause() {
    super.onPause();
    Log.w("onPause","4.alan Pause(Mola) Verildi");
}

// ONSTOP(5)
@Override
protected void onStop() {
    super.onStop();
    Log.w("onStop","5.alan Stop(Durduruldu)");
}

// ONRESTART(6)
@Override
protected void onRestart() {
    super.onRestart();
    Log.w("onRestart","6.alan Restart(Tekrar Başladı)");
}

// ONDESTROY(7)
@Override
protected void onDestroy() {
    super.onDestroy();
    Log.w("onDestroy","7.alan Destroy(Öldü)");
}
} //end MainActivity