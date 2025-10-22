package com.jmceventmanager.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jmceventmanager.Adapters.EventGridAdapter;
import com.jmceventmanager.Database.DatabaseHelper;
import com.jmceventmanager.Models.ModelEvent;
import com.jmceventmanager.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView eventsGridView;
    private DatabaseHelper databaseHelper;
    private TextView tvActiveEvents, tvAllEvents, tvUtilizationRate;
    private FloatingActionButton btnCreateEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupAnimations();
        loadEventData();
        setupEventListeners();
    }

    private void initializeViews() {
        eventsGridView = findViewById(R.id.eventsGridView);
        tvActiveEvents = findViewById(R.id.tvActiveEvents);
        tvAllEvents = findViewById(R.id.tvAllEvents);
        tvUtilizationRate = findViewById(R.id.tvUtilizationRate);
        btnCreateEvent = findViewById(R.id.btnCreateEvent);
        databaseHelper = new DatabaseHelper(this);
    }

    private void setupAnimations() {
        // Frame animation for grid items
        Animation gridAnimation = AnimationUtils.loadAnimation(this, R.anim.grid_item_enter);
        eventsGridView.setAnimation(gridAnimation);
    }

    @SuppressLint("DefaultLocale")
    private void loadEventData() {
        List<ModelEvent> allEvents = databaseHelper.getActiveEvents();


        int activeEventsCount = 0;
        double totalUtilization = 0.0;

        for (ModelEvent event : allEvents) {
            if ("Active".equals(event.getEventStatus())) {
                activeEventsCount++;
            }
            totalUtilization += event.getUtilizationRate();
        }

        double avgUtilization = allEvents.isEmpty() ? 0.0 : totalUtilization / allEvents.size();

        // Update statistics
        tvActiveEvents.setText(String.valueOf(activeEventsCount));
        tvAllEvents.setText(String.valueOf(allEvents.size()));
        tvUtilizationRate.setText(String.format("%.1f%%", avgUtilization));

        // Setup adapter with tween animation
        EventGridAdapter eventAdapter = new EventGridAdapter(this, allEvents);
        eventsGridView.setAdapter(eventAdapter);
    }

    private void setupEventListeners() {
        Button btnViewRegistrations = findViewById(R.id.btnViewRegistrations);

        // Tween animation for button clicks
        final Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale);

        btnCreateEvent.setOnClickListener(v -> {
            v.startAnimation(scaleAnimation);
            Intent intent = new Intent(MainActivity.this, EventManagementActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Fixed: should be slide_out_left
        });

        btnViewRegistrations.setOnClickListener(v -> {
            v.startAnimation(scaleAnimation);
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Fixed: should be slide_out_left
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEventData(); // Refresh data when returning to activity
    }
}