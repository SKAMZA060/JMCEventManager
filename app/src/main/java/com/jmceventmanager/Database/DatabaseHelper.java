package com.jmceventmanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jmceventmanager.Models.ModelEvent;
import com.jmceventmanager.Models.Participant;
import com.jmceventmanager.Models.Registration;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "JMC_Events.db";
    private static final int DATABASE_VERSION = 1;
    // Events table
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_EVENT_ID = "event_id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_CATEGORY = "event_category";
    private static final String COLUMN_EVENT_DATETIME = "event_date_time";
    private static final String COLUMN_VENUE = "venue";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_CURRENT_REGISTRATIONS = "current_registrations";
    private static final String COLUMN_EVENT_STATUS = "event_status";
    private static final String COLUMN_ORGANIZER_NAME = "organizer_name";

    // Participants table
    private static final String TABLE_PARTICIPANTS = "participants";
    private static final String COLUMN_PARTICIPANT_ID = "participant_id";
    private static final String COLUMN_PARTICIPANT_NAME = "participant_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";

    // Registrations table
    private static final String TABLE_REGISTRATIONS = "registrations";
    private static final String COLUMN_REGISTRATION_ID = "registration_id";
    private static final String COLUMN_REGISTRATION_TIMESTAMP = "registration_timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create events table
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT_NAME + " TEXT NOT NULL,"
                + COLUMN_EVENT_CATEGORY + " TEXT NOT NULL,"
                + COLUMN_EVENT_DATETIME + " TEXT NOT NULL,"
                + COLUMN_VENUE + " TEXT NOT NULL,"
                + COLUMN_CAPACITY + " INTEGER NOT NULL,"
                + COLUMN_CURRENT_REGISTRATIONS + " INTEGER DEFAULT 0,"
                + COLUMN_EVENT_STATUS + " TEXT DEFAULT 'Active',"
                + COLUMN_ORGANIZER_NAME + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_EVENTS_TABLE);

        // Create participants table
        String CREATE_PARTICIPANTS_TABLE = "CREATE TABLE " + TABLE_PARTICIPANTS + "("
                + COLUMN_PARTICIPANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PARTICIPANT_NAME + " TEXT NOT NULL,"
                + COLUMN_EMAIL + " TEXT NOT NULL,"
                + COLUMN_PHONE + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_PARTICIPANTS_TABLE);

        // Create registrations table
        String CREATE_REGISTRATIONS_TABLE = "CREATE TABLE " + TABLE_REGISTRATIONS + "("
                + COLUMN_REGISTRATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT_ID + " INTEGER,"
                + COLUMN_PARTICIPANT_ID + " INTEGER,"
                + COLUMN_REGISTRATION_TIMESTAMP + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_EVENT_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + "),"
                + "FOREIGN KEY(" + COLUMN_PARTICIPANT_ID + ") REFERENCES " + TABLE_PARTICIPANTS + "(" + COLUMN_PARTICIPANT_ID + "),"
                + "UNIQUE(" + COLUMN_EVENT_ID + ", " + COLUMN_PARTICIPANT_ID + ")"
                + ")";
        db.execSQL(CREATE_REGISTRATIONS_TABLE);

        // Insert sample data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert sample events
        ContentValues[] sampleEvents = {
                createEventValues("Summer Arts Festival", "Cultural", "2025-07-25 18:00:00",
                        "Central Park Amphitheater", 60, 45, "Active", "Sarah Johnson"),
                createEventValues("Neighborhood Clean-up Drive", "Community Service", "2025-08-05 08:00:00",
                        "Riverside Park", 80, 67, "Active", "Mike Thompson"),
                createEventValues("Community Soccer Tournament", "Sports", "2025-07-28 09:00:00",
                        "Sports Complex Field A", 32, 32, "Full", "David Chen"),
                createEventValues("Poetry Night", "Cultural", "2025-08-08 19:30:00",
                        "Community Library Hall", 30, 12, "Active", "Emily Williams"),
                createEventValues("Digital Marketing Workshop", "Educational", "2025-08-02 14:00:00",
                        "Learning Center Room 201", 25, 18, "Active", "Tech Education Inc."),
                createEventValues("Youth Leadership Summit", "Educational", "2025-08-12 10:00:00",
                        "Convention Center", 50, 0, "Cancelled", "Leadership Foundation")
        };

        for (ContentValues values : sampleEvents) {
            db.insert(TABLE_EVENTS, null, values);
        }
    }

    private ContentValues createEventValues(String name, String category, String datetime,
                                            String venue, int capacity, int registrations,
                                            String status, String organizer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, name);
        values.put(COLUMN_EVENT_CATEGORY, category);
        values.put(COLUMN_EVENT_DATETIME, datetime);
        values.put(COLUMN_VENUE, venue);
        values.put(COLUMN_CAPACITY, capacity);
        values.put(COLUMN_CURRENT_REGISTRATIONS, registrations);
        values.put(COLUMN_EVENT_STATUS, status);
        values.put(COLUMN_ORGANIZER_NAME, organizer);
        return values;
    }

    // Event CRUD operations with transaction management
    public long addEvent(ModelEvent event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.getEventName());
        values.put(COLUMN_EVENT_CATEGORY, event.getEventCategory());
        values.put(COLUMN_EVENT_DATETIME, event.getEventDateTime());
        values.put(COLUMN_VENUE, event.getVenue());
        values.put(COLUMN_CAPACITY, event.getCapacity());
        values.put(COLUMN_ORGANIZER_NAME, event.getOrganizerName());

        return db.insert(TABLE_EVENTS, null, values);
    }

    public List<ModelEvent> getActiveEvents() {
        List<ModelEvent> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_EVENTS +
                        " WHERE " + COLUMN_EVENT_STATUS + " = 'Active'" +
                        " ORDER BY " + COLUMN_EVENT_DATETIME + " ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                ModelEvent event = new ModelEvent();
                event.setEventId(cursor.getInt(0));
                event.setEventName(cursor.getString(1));
                event.setEventCategory(cursor.getString(2));
                event.setEventDateTime(cursor.getString(3));
                event.setVenue(cursor.getString(4));
                event.setCapacity(cursor.getInt(5));
                event.setCurrentRegistrations(cursor.getInt(6));
                event.setEventStatus(cursor.getString(7));
                event.setOrganizerName(cursor.getString(8));
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventList;
    }

    // Registration with transaction management
    public boolean registerParticipantForEvent(Registration registration) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            // First, insert participant if not exists
            long participantId = addParticipant(registration.getParticipant());

            // Check if event has capacity
            ModelEvent event = getEventById(registration.getEventId());
            if (event != null && event.getCurrentRegistrations() < event.getCapacity()) {
                // Insert registration
                ContentValues values = new ContentValues();
                values.put(COLUMN_EVENT_ID, registration.getEventId());
                values.put(COLUMN_PARTICIPANT_ID, participantId);
                values.put(COLUMN_REGISTRATION_TIMESTAMP, registration.getRegistrationTimestamp());

                long result = db.insert(TABLE_REGISTRATIONS, null, values);

                if (result != -1) {
                    // Update event registration count
                    ContentValues updateValues = new ContentValues();
                    updateValues.put(COLUMN_CURRENT_REGISTRATIONS, event.getCurrentRegistrations() + 1);

                    // Update event status if full
                    if (event.getCurrentRegistrations() + 1 >= event.getCapacity()) {
                        updateValues.put(COLUMN_EVENT_STATUS, "Full");
                    }

                    db.update(TABLE_EVENTS, updateValues, COLUMN_EVENT_ID + " = ?",
                            new String[]{String.valueOf(registration.getEventId())});

                    db.setTransactionSuccessful();
                    return true;
                }
            }
            return false;
        } finally {
            db.endTransaction();
        }
    }

    private long addParticipant(Participant participant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTICIPANT_NAME, participant.getParticipantName());
        values.put(COLUMN_EMAIL, participant.getEmail());
        values.put(COLUMN_PHONE, participant.getPhone());
        return db.insert(TABLE_PARTICIPANTS, null, values);
    }

    private ModelEvent getEventById(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS, null, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(eventId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ModelEvent event = new ModelEvent();
            event.setEventId(cursor.getInt(0));
            event.setEventName(cursor.getString(1));
            event.setEventCategory(cursor.getString(2));
            event.setEventDateTime(cursor.getString(3));
            event.setVenue(cursor.getString(4));
            event.setCapacity(cursor.getInt(5));
            event.setCurrentRegistrations(cursor.getInt(6));
            event.setEventStatus(cursor.getString(7));
            event.setOrganizerName(cursor.getString(8));
            cursor.close();
            return event;
        }
        return null;
    }
}