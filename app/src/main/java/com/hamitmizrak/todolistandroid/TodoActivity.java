package com.hamitmizrak.todolistandroid;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {

    // Variable (activity_todo.xml)
    private EditText todoEditTextTask;
    private Button todoButtonAdd;
    private ListView todoListViewTasks;

    // Adapter
    private ArrayList<TaskModel> tasksList = new ArrayList<>();
    private TaskAdapter adapter;

    // Firebase
    private FirebaseDatabase database;
    private DatabaseReference tasksRef;

    // ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ID
        todoEditTextTask = findViewById(R.id.todoEditTextTask);
        todoButtonAdd = findViewById(R.id.todoButtonAdd);
        todoListViewTasks = findViewById(R.id.todoListViewTasks);

        // Firebase
        database=FirebaseDatabase.getInstance();
        tasksRef=database.getReference(PersistDataUrl.FIREBASE_REFERANCE_ROOT); //todolist=Firebase

        Log.e("Database Key: ", tasksRef.getKey());

        // Adapter
        adapter=new TaskAdapter(this,tasksList);
        todoListViewTasks.setAdapter(adapter);

        // ButtonAdd (Yeni Görev butonu)
        todoButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName=todoEditTextTask.getText().toString().trim();
                //Validation
                if(!TextUtils.isEmpty(taskName)){
                    String taskId=tasksRef.push().getKey();
                    TaskModel taskModel=new TaskModel(taskId,taskName,false);
                    tasksRef.child(taskId).setValue(taskModel);
                    todoEditTextTask.setText("");
                }else{
                    Toast.makeText(TodoActivity.this, "Lütfen bir görev giriniz", Toast.LENGTH_LONG).show();
                }//end else
            }//end onClick
        }); //end setOnClickListener

        // Firebase verileri almak istiyorum
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // exists(): Datasnapshoty'ın var olup olmadığını kontrol etmek için yazılır. Eğer veri yoksa veri işlemleri yapılmaz.
                if (snapshot.exists()) {
                    // KEY: Veri anahtarı
                    Log.i("KEY: ", snapshot.getKey());

                    // VALUE: Veri değeri
                    // Eğer Veri varsa
                    if (snapshot.getValue() != null) {
                        // VALUE
                        Log.i("VALUE: ", snapshot.getValue().toString());
                    } else {
                        Log.e("VALUE: ", "Vo value Found");
                    }
                } else {
                    Log.e("VALUE: ", "Snaphot does not exist");
                }

                tasksList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    TaskModel taskModel=dataSnapshot.getValue(TaskModel.class);
                    tasksList.add(taskModel);
                } //end for
                adapter.notifyDataSetChanged();
            } //end onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TodoActivity.this, error.getCode()+" Firebase'den veri alınamadı "+error.getMessage(), Toast.LENGTH_SHORT).show();
            } //end onCancelled
        });//end addValueEventListener

    } //end OnCreate

} //end TodoActivity