package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Calendrier_Entretien extends AppCompatActivity {

    private Button addBtn;
    private CalendarView calendarView;
    private ListView eventsListView;
    private EditText addEventEditText;
    private Map<String, ArrayList<String>> events;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier_entretien);

        addEventEditText = findViewById(R.id.addEventEditText);
        calendarView = findViewById(R.id.calendarView);
        eventsListView = findViewById(R.id.eventsListView);
        addBtn = findViewById(R.id.addBtn);

        events = new HashMap<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        eventsListView.setAdapter(adapter);

        // Initialiser avec quelques événements de test
        addSampleEvents();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = formatDate(year, month, dayOfMonth);
            updateUI(selectedDate);
        });

        addBtn.setOnClickListener(v -> {
            String eventMsg = addEventEditText.getText().toString();
            if (eventMsg.length() > 1) {
                String selectedDate = formatDate(calendarView.getDate());
                addEvent(selectedDate, eventMsg);

                // Mise à jour de l'interface utilisateur
                updateUI(selectedDate);

                addEventEditText.setText("");
                Toast.makeText(this, "Événement ajouté avec succès !", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton backArrowButton = findViewById(R.id.image_view_back_arrow);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer une nouvelle activité ici
                Intent intent = new Intent(Calendrier_Entretien.this , ActivityNavigation.class);
                startActivity(intent);
            }
        });
    }

    private void addSampleEvents() {
        addEvent("03/03/2024", "Rendu TP1 ");

        addEvent("29/04/2024", "Mon anniversaire");
        addEvent("03/03/2024", "RDV");

    }

    private void addEvent(String date, String event) {
        if (events.containsKey(date)) {
            events.get(date).add(event);
        } else {
            ArrayList<String> newEvents = new ArrayList<>();
            newEvents.add(event);
            events.put(date, newEvents);
        }
    }

    private void updateUI(String selectedDate) {
        ArrayList<String> displayEvents = events.get(selectedDate);

        // Mettez à jour l'adaptateur plutôt que d'en créer un nouveau
        adapter.clear();
        if (displayEvents != null) {
            adapter.addAll(displayEvents);
        }
        adapter.notifyDataSetChanged();
    }

    private String formatDate(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date(dateInMillis));
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        return String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
    }
}