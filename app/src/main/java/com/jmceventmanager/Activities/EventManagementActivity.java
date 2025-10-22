package com.jmceventmanager.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.card.MaterialCardView;
import com.jmceventmanager.Database.DatabaseHelper;
import com.jmceventmanager.Models.ModelEvent;
import com.jmceventmanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventManagementActivity extends AppCompatActivity {

    private EditText etEventName, etEventDateTime, etVenue, etCapacity, etOrganizerName;
    private Spinner spinnerCategory, spinnerStatus;
    private Button btnSaveEvent, btnClearForm;
    private MaterialCardView formCard;

    private DatabaseHelper databaseHelper;
    private Calendar eventCalendar;
    private SimpleDateFormat dateTimeFormatter;

    // Event categories and statuses
    private final String[] categories = {"Cultural", "Sports", "Educational", "Community Service"};
    private final String[] statuses = {"Active", "Full", "Cancelled", "Completed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        initializeViews();
        setupToolbar();
        setupSpinners();
        setupDateTimePicker();
        setupAnimations();
        setupEventListeners();
    }

    private void initializeViews() {
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create/Manage Event");

        // Form fields
        etEventName = findViewById(R.id.etEventName);
        etEventDateTime = findViewById(R.id.etEventDateTime);
        etVenue = findViewById(R.id.etVenue);
        etCapacity = findViewById(R.id.etCapacity);
        etOrganizerName = findViewById(R.id.etOrganizerName);

        // Spinners
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerStatus = findViewById(R.id.spinnerStatus);

        // Buttons
        btnSaveEvent = findViewById(R.id.btnSaveEvent);
        btnClearForm = findViewById(R.id.btnClearForm);

        // Card for animations
        formCard = findViewById(R.id.formCard);

        // Database helper
        databaseHelper = new DatabaseHelper(this);

        // Calendar and formatter
        eventCalendar = Calendar.getInstance();
        dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setupSpinners() {
        // Category spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Status spinner
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
    }

    private void setupDateTimePicker() {
        etEventDateTime.setOnClickListener(v -> showDateTimePicker());
        etEventDateTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDateTimePicker();
            }
        });
    }

    private void setupAnimations() {
        // Card entrance animation
        Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        formCard.startAnimation(slideUpAnimation);

        // Button animations
        final Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale);
        btnSaveEvent.setAnimation(scaleAnimation);
        btnClearForm.setAnimation(scaleAnimation);
    }

    private void setupEventListeners() {
        btnSaveEvent.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_scale));
            saveEvent();
        });

        btnClearForm.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_scale));
            clearForm();
        });
    }

    private void showDateTimePicker() {
        // Date picker
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    eventCalendar.set(Calendar.YEAR, year);
                    eventCalendar.set(Calendar.MONTH, month);
                    eventCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    showTimePicker();
                },
                eventCalendar.get(Calendar.YEAR),
                eventCalendar.get(Calendar.MONTH),
                eventCalendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    eventCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    eventCalendar.set(Calendar.MINUTE, minute);
                    etEventDateTime.setText(dateTimeFormatter.format(eventCalendar.getTime()));
                },
                eventCalendar.get(Calendar.HOUR_OF_DAY),
                eventCalendar.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show();
    }

    private void saveEvent() {
        if (!validateForm()) {
            return;
        }

        try {
            // Create new event object
            ModelEvent event = new ModelEvent(
                    etEventName.getText().toString().trim(),
                    spinnerCategory.getSelectedItem().toString(),
                    etEventDateTime.getText().toString().trim(),
                    etVenue.getText().toString().trim(),
                    Integer.parseInt(etCapacity.getText().toString().trim()),
                    etOrganizerName.getText().toString().trim()
            );

            // Set status from spinner
            event.setEventStatus(spinnerStatus.getSelectedItem().toString());

            // Save to database
            long result = databaseHelper.addEvent(event);

            if (result != -1) {
                // Success animation
                Animation successAnimation = AnimationUtils.loadAnimation(this, R.anim.success_bounce);
                btnSaveEvent.startAnimation(successAnimation);

                Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show();

                // Clear form after successful save
                clearForm();

                // Optional: Return to main activity
                // finish();
            } else {
                Toast.makeText(this, "Failed to create event. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid capacity number", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        // Validate event name
        if (TextUtils.isEmpty(etEventName.getText().toString().trim())) {
            etEventName.setError("Event name is required");
            etEventName.requestFocus();
            return false;
        }

        // Validate date and time
        if (TextUtils.isEmpty(etEventDateTime.getText().toString().trim())) {
            etEventDateTime.setError("Event date and time is required");
            etEventDateTime.requestFocus();
            return false;
        }

        // Validate venue
        if (TextUtils.isEmpty(etVenue.getText().toString().trim())) {
            etVenue.setError("Venue is required");
            etVenue.requestFocus();
            return false;
        }

        // Validate capacity
        if (TextUtils.isEmpty(etCapacity.getText().toString().trim())) {
            etCapacity.setError("Capacity is required");
            etCapacity.requestFocus();
            return false;
        }

        try {
            int capacity = Integer.parseInt(etCapacity.getText().toString().trim());
            if (capacity <= 0) {
                etCapacity.setError("Capacity must be greater than 0");
                etCapacity.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            etCapacity.setError("Please enter a valid number");
            etCapacity.requestFocus();
            return false;
        }

        // Validate organizer name
        if (TextUtils.isEmpty(etOrganizerName.getText().toString().trim())) {
            etOrganizerName.setError("Organizer name is required");
            etOrganizerName.requestFocus();
            return false;
        }

        return true;
    }

    private void clearForm() {
        etEventName.setText("");
        etEventDateTime.setText("");
        etVenue.setText("");
        etCapacity.setText("");
        etOrganizerName.setText("");
        spinnerCategory.setSelection(0);
        spinnerStatus.setSelection(0);

        // Reset calendar to current time
        eventCalendar = Calendar.getInstance();

        // Show clear animation
        Animation clearAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        formCard.startAnimation(clearAnimation);

        // Reset animation after clear
        new android.os.Handler().postDelayed(() -> {
            Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            formCard.startAnimation(fadeInAnimation);
        }, 300);

        Toast.makeText(this, "Form cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}