![](https://komarev.com/ghpvc/?username=hamitmizrak)
![readme](./pictures/ana1.png)
![readme](./pictures/ana2.png)

# Android Uygulamasına Hoşgeldiniz
[GitHub](https://github.com/hamitmizrak/Offline_Todolist_Android)
---

[Firebase](https://console.firebase.google.com/project/todolist-44/overview)
---

## ADB  (Aşağıdaki Kodları proje dizinindeki terminalde yazıyorsunuz)
```sh 
adb: Android Debug Bridge
adb kill-server
adb start-server
adb logcat
```
---


## GRADLE (Aşağıdaki Kodları proje dizinindeki terminalde yazıyorsunuz)
```sh 
Build=> Clean Project
Build=> Rebuild Project
File => Invalidate Cache => Hepsini Siliyorsunuz
Uygulamaya Git => Settings => Apps => Tech Android Uygulaması(APK) = Storage&Cache => Clear storage veya clear cache
./gradlew clean    => Cache Temizliği.
./gradlew assembleDebug => Projeyi tekrar çalıştır.
```
---


## Loglama
```sh
verbose<debug<info<warn<error
Log.v("tag","verbose");
Log.d("tag","debug");
Log.i("tag","info");
Log.w("tag","warn");
Log.e("tag","error");
```
---
## Toast
```sh 
   // Toast
   //Toast.makeText(this, "Anasayfaya Hoşgeldiniz", Toast.LENGTH_SHORT).show();
   String welcome=getString(R.string.welcome);
   Toast.makeText(this, welcome, Toast.LENGTH_LONG).show();
```

---
## Live Cycle
```sh 
public class MainActivity extends AppCompatActivity {

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
        String welcome=getString(R.string.welcome);
        Toast.makeText(this, welcome, Toast.LENGTH_LONG).show();
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
```


```
---
## Adapter
```sh 
ADAPTER
```
### Android Adapter Nedir?

Adapter, Android'de veri kaynakları ile kullanıcı arayüzü (UI) bileşenleri arasındaki köprü işlevi gören bir tasarım desenidir. 
Adapter sınıfları, veri setlerini alır ve bu verileri ListView, GridView, RecyclerView gibi UI bileşenlerine bağlar. Adapter'lar, genellikle veri kaynakları ve UI bileşenleri arasındaki veri dönüştürme ve aktarımını yönetir.

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

### Nasıl Çalışır?

Adapter, veri seti ve UI bileşeni arasında bir köprü oluşturur. Örneğin, bir ArrayAdapter kullanarak bir string dizisini ListView'a bağladığınızda, her bir string öğesi için bir TextView oluşturulur ve ListView'da görüntülenir.

### Temel Kullanım Örneği

#### ArrayAdapter Kullanımı:

```java
// MainActivity.java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Veriyi tanımla
        String[] data = {"Apple", "Banana", "Cherry", "Date", "Elderberry"};

        // ListView'u bul
        ListView listView = findViewById(R.id.listView);

        // ArrayAdapter oluştur
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            data
        );

        // Adapter'ı ListView'a bağla
        listView.setAdapter(adapter);
    }
}
```

#### BaseAdapter Kullanımı:

```java
// CustomAdapter.java
public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;

    public CustomAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(data.get(position));

        return convertView;
    }
}

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> data = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));

        ListView listView = findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, data);
        listView.setAdapter(adapter);
    }
}
```

### Özet

Adapter, Android uygulamalarında veri setlerini UI bileşenlerine bağlayan bir yapı olarak büyük öneme sahiptir. Farklı türde adapter'lar farklı veri kaynakları ve UI bileşenleri için kullanılır. Adapter'lar, veri setini yönetir, dönüştürür ve UI bileşenine uygun şekilde sunar.


---
## Toast
```sh 

```
---
## Toast
```sh 

```

---
## Toast
```sh 

```
---
## Toast
```sh 

```