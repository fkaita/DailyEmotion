package com.example.dailyemotion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BehaviorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, TimePickerDialog.OnTimeSetListener {
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);



        TextView txtBehavior = findViewById(R.id.txtBehavior);

        // Setting back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting spinner of behavior types
        Spinner spiBehavior = findViewById(R.id.spiBehavior);
        ArrayAdapter<CharSequence> behaviorAdapter = ArrayAdapter.createFromResource(this,
                R.array.activities, android.R.layout.simple_spinner_item);
        behaviorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiBehavior.setAdapter(behaviorAdapter);

        // Setting spinner of start or stop
        Spinner spiStartStop = findViewById(R.id.spiStartStop);
        ArrayAdapter<CharSequence> startStopAdapter = ArrayAdapter.createFromResource(this,
                R.array.startStop, android.R.layout.simple_spinner_item);
        startStopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiStartStop.setAdapter(startStopAdapter);

        // Save button
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == saveBtn) {
                    Date date = new Date();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                    String timeSaved = formatter.format(date);
                    String behavior = (String) spiBehavior.getSelectedItem();
                    String startStop = (String) spiStartStop.getSelectedItem();

                    OpenHelper helper = new OpenHelper(getApplicationContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    if(db == null){
                        helper.onUpgrade(db, 0, 1);
                    }
                    insertData(db, timeSaved, behavior, startStop, time);
                    finish();
                }
            }
        });
    }

    private void insertData(SQLiteDatabase db, String timeSaved, String behavior, String startEnd, String time) {
        ContentValues values = new ContentValues();
        values.put("timeSaved", timeSaved);
        values.put("behavior", behavior);
        values.put("startEnd", startEnd);
        values.put("time", time);

        db.insert("behavior_db", null, values);
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView txtTime = findViewById(R.id.txtTime);
        time = String.format("%02d:%02d", hourOfDay, minute);
        txtTime.setText(time);
    }

}