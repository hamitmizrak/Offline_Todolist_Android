package com.hamitmizrak.todolistandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;


/*
Adapter: Android'de veri kaynakları ile kullanıcı arayüzü(UI) arasındaki köprü işlemi gören bir tasarım deseni(design pattern)
Adapter sınıfları: Veri setleri alır ve bu veri setlerini GridView, RecyclerView, ListView v.b UI bileşenlerine bağlar.

### Android Adapter Nedir?
Adapter, Android'de veri kaynakları ile kullanıcı arayüzü (UI) bileşenleri arasındaki köprü işlevi gören bir tasarım desenidir. Adapter sınıfları, veri setlerini alır ve bu verileri ListView, GridView, RecyclerView gibi UI bileşenlerine bağlar. Adapter'lar, genellikle veri kaynakları ve UI bileşenleri arasındaki veri dönüştürme ve aktarımını yönetir.

### Adapter Türleri
1. **ArrayAdapter**:
   - Basit veri setlerini (örneğin, string dizisi) bir ListView veya Spinner'a bağlamak için kullanılır.
   - Standart bir düzen (layout) sağlar ve her öğeyi basit bir TextView'e dönüştürür.

2. **SimpleAdapter**:
   - Daha karmaşık veri yapıları (örneğin, HashMap veya ArrayList) için kullanılır.
   - Özelleştirilmiş düzenlerle verilerin nasıl görüntüleneceğini tanımlar.

3. **CursorAdapter**:
   - Veritabanından (genellikle SQLite) alınan verileri ListView veya GridView gibi bileşenlere bağlamak için kullanılır.
   - Cursor kullanarak veritabanı sorgularından dönen verileri işler.

4. **BaseAdapter**:
   - Kendi özelleştirilmiş adapter'larınızı oluşturmak için temel sınıf olarak kullanılır.
   - ListView, GridView veya Spinner gibi bileşenlere daha fazla esneklik sağlar.

5. **RecyclerView.Adapter**:
   - RecyclerView bileşeni için kullanılır.
   - Daha modern ve esnek bir yaklaşımla büyük veri setlerini yönetir.

### Adapter'ın Temel Görevleri
1. **Veri Sağlama**: Adapter, veri setini sağlar ve UI bileşenlerine aktarır.
2. **View Sağlama**: Adapter, her veri öğesi için uygun bir View (görünüm) oluşturur.
3. **Veri Yenileme**: Adapter, veri setindeki değişiklikleri izler ve UI bileşenini günceller.

Adapter, Android uygulamalarında veri setlerini UI bileşenlerine bağlayan bir yapı olarak büyük öneme sahiptir.
Farklı türde adapter'lar farklı veri kaynakları ve UI bileşenleri için kullanılır.
Adapter'lar, veri setini yönetir, dönüştürür ve UI bileşenine uygun şekilde sunar.
*/
public class TaskAdapter extends ArrayAdapter<TaskModel> {
    // Variable
    private final Activity context;
    private final ArrayList<TaskModel> tasks;

    // Parametreli constructor
    public TaskAdapter(Activity _context, ArrayList<TaskModel> _tasks) {
        super(_context, R.layout.task_item_custom_layout, _tasks);
        this.context = _context;
        this.tasks = _tasks;
    }

    // getView
   /* @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }*/

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.task_item_custom_layout, null, true);

        CheckBox checkBox = rowView.findViewById(R.id.checkBoxTask);
        Button buttonDelete = rowView.findViewById(R.id.buttonDeleteTask);
        Button buttonUpdate = rowView.findViewById(R.id.buttonUpdateTask);

        final TaskModel taskModel = tasks.get(position);
        checkBox.setText(taskModel.getName());
        checkBox.setChecked(taskModel.isCompleted());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            taskModel.setCompleted(isChecked);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(PersistDataUrl.FIREBASE_REFERANCE_ROOT).child(taskModel.getId());
            databaseReference.setValue(taskModel);
        }); //end setOnCheckedChangeListener

        //DELETE
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReferenceDelete = FirebaseDatabase.getInstance().getReference(PersistDataUrl.FIREBASE_REFERANCE_ROOT).child(taskModel.getId());
                databaseReferenceDelete.removeValue();
                tasks.remove(position);
                notifyDataSetChanged();
            }
        });

        // UPDATE
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(taskModel);
            }
        });
        return rowView;
    } //end getView


    // Dialog Update
    private void showUpdateDialog(final TaskModel taskModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_custom_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTask = dialogView.findViewById(R.id.editTextUpdateTask);
        editTextTask.setText(taskModel.getName());

        dialogBuilder.setTitle("Yapılacak İşi Güncelle");
        dialogBuilder.setMessage("Yapılacak Yeni Görevi Giriniz");
        dialogBuilder.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                String updatedTask = editTextTask.getText().toString().trim();

                //Validation
                if (!updatedTask.isEmpty()) {
                    taskModel.setName(updatedTask);
                    DatabaseReference databaseReferenceUpdated = FirebaseDatabase.getInstance().getReference(PersistDataUrl.FIREBASE_REFERANCE_ROOT).child(taskModel.getId());
                    databaseReferenceUpdated.setValue(taskModel);
                    notifyDataSetChanged();
                } //end if
            } //end onClick
        }); //end setPositiveButton
        dialogBuilder.setNegativeButton("Güncelleme İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Güncelleme İptal", Toast.LENGTH_SHORT).show();
            }
        }); //end setNegativeButton
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    } //end showUpdateDialog


} //end TaskAdapter

