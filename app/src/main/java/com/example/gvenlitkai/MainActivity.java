package com.example.gvenlitkai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText urlEditText;
    private Button openButton;
    private TextView textViewResult;
    private ProgressBar progressBar;
    private FirebaseFirestore firestore;
    private ListenerRegistration firestoreListener; // Firestore dinleyicisi

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        urlEditText = findViewById(R.id.text_url);
        Button saveButton = findViewById(R.id.button_send);
        openButton = findViewById(R.id.button_open);
        textViewResult = findViewById(R.id.text_sonuc);
        progressBar = findViewById(R.id.progressBar); // ProgressBar tanımlaması
        firestore = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.GONE); // Başlangıçta gizle
        openButton.setVisibility(View.GONE);

        saveButton.setOnClickListener(v -> saveUrl());

        openButton.setOnClickListener(v -> openUrl());

        Uri data = getIntent().getData();
        if (data != null) {
            String url = data.toString();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                // Veri "http://" veya "https://" ile başlamıyorsa, varsayılan olarak "http://" ekleyin
                url = "http://" + url;
            }
            System.out.println(url);
            urlEditText.setText(url);
            saveUrl();
        }

    }

    private void openUrl() {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Google Chrome'un paket ismini belirleme
        intent.setPackage("com.android.chrome");

        // Chrome yüklüyse URL'yi Chrome ile aç, değilse kullanıcıya bir seçenek sun
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Chrome yüklü değilse, kullanıcıya URL'yi açacak başka bir uygulama seçme imkanı sun
            intent.setPackage(null);
            startActivity(Intent.createChooser(intent, "Bir tarayıcı seçiniz"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firestoreListener != null) {
            firestoreListener.remove(); // Dinleyiciyi kaldır
        }
    }
    private void saveUrl() {
        openButton.setVisibility(View.GONE);
        url = urlEditText.getText().toString().trim();
        if (!url.isEmpty() && isValidUrl(url)) {
            progressBar.setVisibility(View.VISIBLE);
            HashMap<String, Object> data = new HashMap<>();
            data.put("url", url);
            data.put("sonuc", null);

            firestore.collection("URL").add(data).addOnSuccessListener(documentReference -> {
                listenForResults(documentReference.getId());
                urlEditText.setText(""); // EditText'i temizle
            }).addOnFailureListener(e -> {
                Toast.makeText(MainActivity.this, "URL kaydedilemedi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            });
        } else {
            Toast.makeText(this, "Geçersiz URL", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidUrl(String url) {
        String urlPattern = "^(http|https|ftp)://.*$"; // Basit bir URL regex'i
        return url.matches(urlPattern);
    }
    private void listenForResults(String documentId) {
        if (firestoreListener != null) {
            firestoreListener.remove(); // Eski dinleyiciyi kaldır
        }
        firestoreListener = firestore.collection("URL").document(documentId)
                .addSnapshotListener((documentSnapshot, e) -> {
                    progressBar.setVisibility(View.GONE);
                    if (e != null) {
                        textViewResult.setText("Dinleme hatası: " + e.getMessage());
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String sonuc = documentSnapshot.getString("sonuc");
                        if (sonuc != null) {
                            textViewResult.setText("Güvenli Olma " + sonuc);
                            openButton.setVisibility(View.VISIBLE);
                        } else {
                            textViewResult.setText("Sonuç bekleniyor...");
                        }
                    }
                });
    }
}